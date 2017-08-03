package me.grechka.yamblz.yamblzweatherapp.domain;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by Grechka on 19.07.2017.
 */

public interface WeatherInteractor {
    Flowable<City> getCity();

    Single<Weather> updateWeather();
    Flowable<Weather> getCachedWeather();

    Observable<Weather> getForecast();

    int[] getModes();
}
