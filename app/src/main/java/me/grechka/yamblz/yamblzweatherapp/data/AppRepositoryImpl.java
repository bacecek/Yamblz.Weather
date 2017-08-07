package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import me.grechka.yamblz.yamblzweatherapp.data.database.AppDatabase;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.CityEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.ForecastEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.WeatherEntity;
import me.grechka.yamblz.yamblzweatherapp.data.storages.Storage;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;
import me.grechka.yamblz.yamblzweatherapp.models.response.places.SuggestionResponse;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;
import me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi;

import static me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi.API_KEY;

/**
 * Created by Grechka on 16.07.2017.
 */

public class AppRepositoryImpl implements AppRepository {

    private static final String TAG = AppRepositoryImpl.class.getCanonicalName();

    private CityEntity city = CityEntity.DEFAULT;
    private WeatherEntity weather;

    private AppDatabase database;
    private Storage<String> prefs;

    private WeatherApi weatherApi;
    private SuggestApi suggestApi;

    public AppRepositoryImpl(@NonNull AppDatabase database,
                             @NonNull Storage<String> prefs,
                             @NonNull WeatherApi weatherApi,
                             @NonNull SuggestApi suggestApi) {
        this.prefs = prefs;
        this.database = database;
        this.weatherApi = weatherApi;
        this.suggestApi = suggestApi;

        this.database
                .cityDao()
                .findActive()
                .subscribe(city -> {
                    this.city = city;
                });
    }

    @Override
    public Completable addCity(@NonNull City city) {
        return Completable
                .fromAction(() -> this.database
                        .cityDao()
                        .insert(CityEntity.fromCity(city, false)))
            .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<City> getCity() {
        return database.cityDao().findActive()
                .map(CityEntity::toCity);
    }

    @Override
    public Flowable<List<City>> getCities() {
        return database.cityDao().find()
                .flatMapSingle(cities -> Flowable.fromIterable(cities)
                .map(CityEntity::toCity)
                .toList());
    }

    @Override
    public Completable removeCity(@NonNull City city) {
        return Completable.fromAction(() ->
        database.cityDao().delete(city.getPlaceId(),
                city.getLocation().getLatitude(), city.getLocation().getLongitude()));
    }

    @Override
    public Completable markCityAsActive(@NonNull City city) {
        CityLocation location = city.getLocation();

        CityEntity inactiveCity = this.city;
        inactiveCity.setActive(false);

        return database.cityDao()
                .findByCoordinates(city.getPlaceId(),
                        location.getLatitude(), location.getLongitude())
                .flatMapCompletable(newEntity -> {
                    newEntity.setActive(true);
                    this.city = newEntity;

                    return Completable
                            .fromAction(() -> {
                                if (inactiveCity != CityEntity.DEFAULT)
                                    this.database.cityDao().update(inactiveCity);
                                this.database.cityDao().update(newEntity);
                            });
                });
    }

    @Override
    public Single<Weather> getWeather() {
        return this.getCachedWeather()
                .onErrorResumeNext(this.getNetworkWeather());
    }

    @Override
    public Single<Weather> getCachedWeather() {
        return database.cityDao()
                .findActive()
                .onErrorReturn(t -> CityEntity.DEFAULT) //// FIXME: 05/08/2017 create coordinates provided address
                .flatMapSingle(city -> {
                    this.city = city;
                    return database.weatherDao().findWeatherByCity(city.getUid());
                })
                .map(weather -> {
                    this.weather = weather;
                    return WeatherEntity.toWeather(weather);
                })
                .firstOrError();
    }

    @Override
    public Single<Weather> getNetworkWeather() {
        return this
                .getWeatherByLocation(city.getLatitude(), city.getLongitude())
                .doOnSuccess(weather -> {
                    this.weather = WeatherEntity.fromWeather(weather, city, false);
                    database.weatherDao().insert(this.weather);
                    }
                );
    }

    @Override
    public Single<List<Weather>> getForecast() {
        return this.getCachedForecasts()
                .flatMap(list -> {
                   if (list.isEmpty()) return this.getNetworkForecasts();
                   return Single.just(list);
                });
    }

    @Override
    public Single<List<Weather>> getCachedForecasts() {
        return database.forecastDao()
                .getAll(this.city.getUid())
                .flatMap(list -> Observable.fromIterable(list)
                            .map(ForecastEntity::toWeather).toList());
    }

    @Override
    public Single<List<Weather>> getNetworkForecasts() {
        return weatherApi
                .getForecastByLocation(city.getLatitude(), city.getLongitude(), API_KEY)
                .doOnSuccess(forecasts -> database.forecastDao().delete())
                .flatMapObservable(forecast -> Observable.fromIterable(forecast.getForecastList()))
                .map(weather -> new Weather.Builder()
                        .weatherId(weather.getWeather().get(0).getId())
                        .temperature(weather.getMain().getTemp())
                        .updateTime(TimeUnit.SECONDS.toMillis(weather.getUpdateTime()))
                        .sunRiseTime(this.weather.getSunrise())
                        .sunSetTime(this.weather.getSunset())
                        .build())
                .doOnNext(weather -> {
                    ForecastEntity entity = ForecastEntity.fromWeather(weather, this.city);
                    database.forecastDao().insert(entity);
                })
                .toList();
    }

    //network
    @Override
    public Single<Weather> getWeatherByLocation(double latitude, double longitude) {
        return weatherApi
                .getWeatherByLocation(latitude, longitude, API_KEY)
                .map(weather -> new Weather.Builder()
                        .weatherId(weather.getWeather().get(0).getId())
                        .temperature(weather.getMain().getTemp())
                        .minTemperature(weather.getMain().getTempMin())
                        .maxTemperature(weather.getMain().getTempMax())
                        .humidity(weather.getMain().getHumidity())
                        .pressure(weather.getMain().getPressure())
                        .windSpeed(weather.getWind().getSpeed())
                        .sunRiseTime(TimeUnit.SECONDS.toMillis(weather.getSunsetAndSunrise().getSunriseTime()))
                        .sunSetTime(TimeUnit.SECONDS.toMillis(weather.getSunsetAndSunrise().getSunsetTime()))
                        .build());
    }

    @Override
    public Single<CityLocation> obtainCityLocation(@NonNull String cityId) {
        return suggestApi.obtainCity(cityId, SuggestApi.API_KEY)
                .map(response -> response.getInfo().getGeometry().getLocation());
    }

    @Override
    public Observable<City> obtainSuggestedCities(@NonNull String input) {
        return suggestApi
                .obtainSuggestedCities(input, SuggestApi.API_TYPES, SuggestApi.API_KEY)
                .map(SuggestionResponse::getPredictions)
                .flatMapObservable(Observable::fromIterable)
                .map(place -> new City.Builder()
                        .placeId(place.getPlaceId())
                        .title(place.getPlaceInfo().getMainText())
                        .extendedTitle(place.getDescription())
                        .build());
    }

    //prefs
    @Override
    public void setFrequency(int frequency) {
        prefs.put(FREQUENCY_KEY, frequency);
    }

    @Override
    public int getFrequency() {
        return prefs.getInteger(FREQUENCY_KEY, 60);
    }

    @Override
    public void saveTemperatureUnits(int units) {
        prefs.put(UNITS_TEMPERATURE_PREFS_KEY, units);
    }

    @Override
    public int getTemperatureUnits() {
        return prefs.getInteger(UNITS_TEMPERATURE_PREFS_KEY, 0);
    }

    @Override
    public void savePressureUnits(int units) {
        prefs.put(UNITS_PRESSURE_PREFS_KEY, units);
    }

    @Override
    public int getPressureUnits() {
        return prefs.getInteger(UNITS_PRESSURE_PREFS_KEY, 0);
    }

    @Override
    public void saveSpeedUnits(int units) {
        prefs.put(UNITS_SPEED_PREFS_KEY, units);
    }

    @Override
    public int getSpeedUnits() {
        return prefs.getInteger(UNITS_SPEED_PREFS_KEY, 0);
    }
}
