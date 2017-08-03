package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import me.grechka.yamblz.yamblzweatherapp.data.database.AppDatabase;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.CityEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.WeatherEntity;
import me.grechka.yamblz.yamblzweatherapp.data.storages.PrefsStorage;
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
    }

    @Override
    public Completable addCity(@NonNull City city) {
        return Completable
                .fromAction(() -> this.database.cityDao().insert(CityEntity.fromCity(city, false)))
            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<City> getCities() {
        return database.cityDao()
                .find()
                .flatMapObservable(Observable::fromIterable)
                .map(CityEntity::toCity);
    }

    @Override
    public Completable markAsActive(@NonNull City city) {
        CityEntity inactiveCity = this.city;
        inactiveCity.setActive(false);

        return database.cityDao()
                .find(city.getPlaceId(),
                        city.getLocation().getLatitude(),
                        city.getLocation().getLongitude())
                .flatMapCompletable(newEntity -> {
                    newEntity.setActive(true);
                    this.city = newEntity;

                    Log.d(TAG, newEntity.toString());
                    return Completable
                            .fromAction(() -> {
                                this.database.cityDao().update(inactiveCity);
                                this.database.cityDao().update(newEntity);
                            });
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<City> getCity() {
        return database.cityDao()
                .findActive()
                .onErrorReturn(t -> CityEntity.DEFAULT)
                .map(CityEntity::toCity);
    }

    @Override
    public Single<Weather> getWeatherByLocation(double latitude, double longitude) {
        return weatherApi
                .getWeatherByLocation(latitude, longitude, API_KEY)
                .map(response -> new Weather.Builder()
                                    .temperature(response.getMain().getTemp())
                                    .humidity(response.getMain().getHumidity())
                                    .minTemperature(response.getMain().getTempMin())
                                    .maxTemperature(response.getMain().getTempMax())
                                    .pressure(response.getMain().getPressure())
                                    .windSpeed(response.getWind().getSpeed())
                                    .build());
    }

    @Override
    public Single<Weather> getCurrentWeather() {
        return Single.just(WeatherEntity.toWeather(weather));
    }

    @Override
    public Flowable<Weather> getCachedWeather() {
        return database.cityDao()
                .findActive()
                .onErrorReturn(t -> CityEntity.DEFAULT)
                .flatMapSingle(entity -> {
                    this.city = entity;
                    return database.weatherDao().findCurrent(entity.getUid());
                })
                .map(WeatherEntity::toWeather);
    }

    @Override
    public Observable<Weather> getForecast() {
        return weatherApi.getForecastByLocation(city.getLatitude(), city.getLongitude(), API_KEY)
                .flatMapObservable(forecast -> Observable.fromIterable(forecast.getForecastList()))
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
    public Single<Weather> updateWeather() {
        return weatherApi
                .getWeatherByLocation(city.getLatitude(), city.getLongitude(), API_KEY)
                .map(weather -> {
                    Weather current = new Weather.Builder()
                            .weatherId(weather.getWeather().get(0).getId())
                            .temperature(weather.getMain().getTemp())
                            .minTemperature(weather.getMain().getTempMin())
                            .maxTemperature(weather.getMain().getTempMax())
                            .humidity(weather.getMain().getHumidity())
                            .pressure(weather.getMain().getPressure())
                            .windSpeed(weather.getWind().getSpeed())
                            .sunRiseTime(TimeUnit.SECONDS.toMillis(weather.getSunsetAndSunrise().getSunriseTime()))
                            .sunSetTime(TimeUnit.SECONDS.toMillis(weather.getSunsetAndSunrise().getSunsetTime()))
                            .build();

                    this.weather = WeatherEntity.fromWeather(current, city, false);
                    database.weatherDao().insert(this.weather);

                    return current;
                });
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

    @Override
    public void setFrequency(int frequency) {
        prefs.put(FREQUENCY_KEY, frequency);
    }

    @Override
    public String getFrequency() {
        return String.valueOf(prefs.getInteger(FREQUENCY_KEY, 60));
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
