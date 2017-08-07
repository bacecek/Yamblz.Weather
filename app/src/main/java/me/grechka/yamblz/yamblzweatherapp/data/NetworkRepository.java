package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;
import me.grechka.yamblz.yamblzweatherapp.models.response.forecast.ForecastResponse;
import retrofit2.http.Query;

/**
 * Created by alexander on 07/08/2017.
 */

public interface NetworkRepository {
    Single<CityLocation> obtainCityLocation(@NonNull String cityId);
    Observable<City> obtainSuggestedCities(@NonNull String input);
    Single<Weather> getWeatherByLocation(double latitude, double longitude);
    Single<ForecastResponse> getForecastByLocation(double latitude, double lon, String apiKey);
}
