package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;

/**
 * Created by Grechka on 16.07.2017.
 */

public interface AppRepository {
    //constants
    String UNITS_TEMPERATURE_PREFS_KEY = "app.prefs.temperature.units";
    String UNITS_PRESSURE_PREFS_KEY = "app.prefs.pressure.units";
    String UNITS_SPEED_PREFS_KEY = "app.prefs.speed.units";
    String FREQUENCY_KEY = "keys.frequency";

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

    //network
    Single<CityLocation> obtainCityLocation(@NonNull String cityId);
    Observable<City> obtainSuggestedCities(@NonNull String input);
    Single<Weather> getWeatherByLocation(double latitude, double longitude);

    //preferences
    void setFrequency(int frequency);
    String getFrequency();

    void saveTemperatureUnits(int units);
    int getTemperatureUnits();

    void savePressureUnits(int units);
    int getPressureUnits();

    void saveSpeedUnits(int units);
    int getSpeedUnits();
}
