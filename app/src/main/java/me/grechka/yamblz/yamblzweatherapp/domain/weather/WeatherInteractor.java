package me.grechka.yamblz.yamblzweatherapp.domain.weather;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by Grechka on 19.07.2017.
 */

public interface WeatherInteractor {
    Flowable<City> getCity();

    Single<List<Weather>> getForecast();
    Single<List<Weather>> updateForecast();

    Single<Weather> getWeather();
    Single<Weather> updateWeather();

    boolean isUnitChanged();
    int[] getModes();
}
