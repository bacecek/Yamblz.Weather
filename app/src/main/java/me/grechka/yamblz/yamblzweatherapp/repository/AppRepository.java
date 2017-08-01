package me.grechka.yamblz.yamblzweatherapp.repository;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.models.response.CurrentWeatherResponse;
import me.grechka.yamblz.yamblzweatherapp.models.response.CityResponseModel;

/**
 * Created by Grechka on 16.07.2017.
 */

public interface AppRepository {

    City getCity();
    void saveCity(@NonNull City city);

    Single<CurrentWeather> getCurrentWeather();
    Single<CurrentWeather> updateCurrentWeather();
    Single<CurrentWeather> getSavedCurrentWeather();

    Single<CurrentWeatherResponse> getWeatherByLocation(double latitude, double longitude);
    Single<CityResponseModel> obtainCityInfo(@NonNull String cityId);
    Observable<City> obtainSuggestedCities(@NonNull String input);
}
