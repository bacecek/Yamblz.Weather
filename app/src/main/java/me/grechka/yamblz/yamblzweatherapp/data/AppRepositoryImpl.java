package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public Completable saveCity(@NonNull City city) {
        CityEntity inactiveCity = this.city;
        inactiveCity.setActive(false);

        this.city = CityEntity.fromCity(city, true);

        return Completable
                .fromAction(() -> {
                    this.database.cityDao().update(inactiveCity);
                    long id = this.database.cityDao().insert(this.city);
                    this.city.setUid(id);
                })
            .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<City> getCity() {
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
    public Single<Weather> getSavedCurrentWeather() {
        return database.cityDao()
                .findActive()
                .onErrorReturn(t -> CityEntity.DEFAULT)
                .flatMap(entity -> {
                    this.city = entity;
                    Log.d("FIND", this.city.toString());
                    return database.weatherDao().findCurrent(entity.getUid());
                })
                .map(WeatherEntity::toWeather);
    }

    @Override
    public Single<Weather> updateCurrentWeather() {
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
        prefs.put(PrefsStorage.FREQUENCY_KEY, frequency);
    }

    @Override
    public String getFrequency() {
        return String.valueOf(prefs.getInteger(PrefsStorage.FREQUENCY_KEY, 60));
    }
}
