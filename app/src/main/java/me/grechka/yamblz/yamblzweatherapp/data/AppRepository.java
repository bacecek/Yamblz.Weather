package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;

/**
 * Created by Grechka on 16.07.2017.
 */

public interface AppRepository {
    Single<City> getCity();
    Completable saveCity(@NonNull City city);

    Single<Weather> getCurrentWeather();
    Single<Weather> updateCurrentWeather();
    Single<Weather> getSavedCurrentWeather();

    //network
    Single<CityLocation> obtainCityLocation(@NonNull String cityId);
    Observable<City> obtainSuggestedCities(@NonNull String input);
    Single<Weather> getWeatherByLocation(double latitude, double longitude);

    //preferences
    void setFrequency(int frequency);
    String getFrequency();
}
