package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by alexander on 07/08/2017.
 */

public interface DatabaseRepository {
    Flowable<City> getCity();
    Flowable<List<City>> getCities();

    Completable addCity(@NonNull City city);
    Completable removeCity(@NonNull City city);
    Completable markCityAsActive(@NonNull City city);

    Single<Weather> getWeather();
    Single<Weather> getCachedWeather();
    Single<Weather> getNetworkWeather();

    Single<List<Weather>> getForecast();
    Single<List<Weather>> getCachedForecasts();
    Single<List<Weather>> getNetworkForecasts();
}
