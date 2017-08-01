package me.grechka.yamblz.yamblzweatherapp.data.net;

import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.response.CurrentWeatherResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Grechka on 14.07.2017.
 */

public interface WeatherApi {

    String API_KEY = "847aa9acae58b3e1ccd9da7ef3fc4d01";
    String UNITS_DEFAULT = "metric";

    @GET("data/2.5/weather")
    Single<CurrentWeatherResponse> getWeatherByLocation(@Query("lat") double latitude,
                                                        @Query("lon") double lon,
                                                        @Query("units") String units,
                                                        @Query("appid") String apiKey);
}
