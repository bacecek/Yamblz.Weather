package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;
import me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;
import me.grechka.yamblz.yamblzweatherapp.models.response.forecast.ForecastResponse;
import me.grechka.yamblz.yamblzweatherapp.models.response.places.SuggestionResponse;

import static me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi.API_KEY;

/**
 * Created by alexander on 08/08/2017.
 */

public class NetworkRepositoryImpl implements NetworkRepository {

    private WeatherApi weatherApi;
    private SuggestApi suggestApi;

    public NetworkRepositoryImpl(@NonNull WeatherApi weatherApi,
                                 @NonNull SuggestApi suggestApi) {
        this.weatherApi = weatherApi;
        this.suggestApi = suggestApi;
    }

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
    public Single<ForecastResponse> getForecastByLocation(double latitude, double lon, String apiKey) {
        return weatherApi.getForecastByLocation(latitude, lon, apiKey);
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
}
