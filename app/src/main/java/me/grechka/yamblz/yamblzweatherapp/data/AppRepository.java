package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;

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
    Observable<City> getCities();
    Completable addCity(@NonNull City city);
    Completable markAsActive(@NonNull City city);

    Observable<Weather> getForecast();
    Single<Weather> getCurrentWeather();
    Single<Weather> updateWeather();
    Flowable<Weather> getCachedWeather();

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
