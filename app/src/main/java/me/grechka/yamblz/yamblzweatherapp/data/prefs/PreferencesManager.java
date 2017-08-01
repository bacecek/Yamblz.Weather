package me.grechka.yamblz.yamblzweatherapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.models.response.CityLocation;

/**
 * Created by Grechka on 18.07.2017.
 */

public class PreferencesManager {

    private String CITY_ID_DEFAULT_KEY = "ChIJybDUc_xKtUYRTM9XV8zWRD0";
    private String CITY_TITLE_DEFAULT_KEY = "Moscow";
    private String CITY_DESCRIPTION_DEFAULT_KEY = "Moscow, Russia";
    private String CITY_LOCATION_LATITUDE_DEFAULT_KEY = "55.755826";
    private String CITY_LOCATION_LONGITUDE_DEFAULT_KEY = "37.6173";

    private String CITY_ID_PREFS_KEY = "keys.prefs.city.placeid";
    private String CITY_TITLE_PREFS_KEY = "keys.prefs.city.title";
    private String CITY_DESCRIPTION_PREFS_KEY = "keys.prefs.city.description";
    private String CITY_LOCATION_LATITUDE_PREFS_KEY = "keys.prefs.city.location.latitude";
    private String CITY_LOCATION_LONGITUDE_PREFS_KEY = "keys.prefs.city.location.longitude";

    private final String PREFERENCES_TAG = "weather_preferences";
    private final String FREQUENCY_TAG = "frequency";
    private final String FREQUENCY_DEFAULT = "60";
    private final String TEMPERATURE_TAG = "temperature";
    private final String DESCRIPTION_TAG = "descdription";
    private final String HUMIDITY_TAG = "humidity";
    private final String MIN_TEMP_TAG = "min_temp";
    private final String MAX_TEMP_TAG = "max_temp";
    private final String WIND_TAG = "wind";
    private final String NO_INFORMATION = "-";

    private SharedPreferences preferences;

    public PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE);
    }

    public String getUpdateFrequency() {
        return preferences.getString(FREQUENCY_TAG, FREQUENCY_DEFAULT);
    }

    public void putUpdateFrequency(String frequency) {
        preferences.edit().putString(FREQUENCY_TAG, frequency).apply();
    }

    public void saveCity(@NonNull City city) {
        preferences.edit()
                .putString(CITY_ID_PREFS_KEY, city.getPlaceId())
                .putString(CITY_TITLE_PREFS_KEY, city.getTitle())
                .putString(CITY_DESCRIPTION_PREFS_KEY, city.getExtendedTitle())
                .putString(CITY_LOCATION_LATITUDE_PREFS_KEY, String.valueOf(city.getLocation().getLatitude()))
                .putString(CITY_LOCATION_LONGITUDE_PREFS_KEY, String.valueOf(city.getLocation().getLongitude()))
                .apply();
    }

    public City getCurrentCity() {
        CityLocation location = new CityLocation(
                Double.valueOf(preferences.getString(CITY_LOCATION_LATITUDE_PREFS_KEY, CITY_LOCATION_LATITUDE_DEFAULT_KEY)),
                Double.valueOf(preferences.getString(CITY_LOCATION_LONGITUDE_PREFS_KEY, CITY_LOCATION_LONGITUDE_DEFAULT_KEY))
        );

        return new City.Builder()
                .placeId(preferences.getString(CITY_ID_PREFS_KEY, CITY_ID_DEFAULT_KEY))
                .title(preferences.getString(CITY_TITLE_PREFS_KEY, CITY_TITLE_DEFAULT_KEY))
                .extendedTitle(preferences.getString(CITY_DESCRIPTION_PREFS_KEY, CITY_DESCRIPTION_DEFAULT_KEY))
                .location(location)
                .build();
    }

    public CurrentWeather getCurrentWeather() {
        return new CurrentWeather(
                preferences.getString(TEMPERATURE_TAG, NO_INFORMATION),
                preferences.getString(DESCRIPTION_TAG, NO_INFORMATION),
                preferences.getString(HUMIDITY_TAG, NO_INFORMATION),
                preferences.getString(MIN_TEMP_TAG, NO_INFORMATION),
                preferences.getString(MAX_TEMP_TAG, NO_INFORMATION),
                preferences.getString(WIND_TAG, NO_INFORMATION)
        );
    }

    public void putCurrentWeather(CurrentWeather currentWeather) {
        preferences.edit().putString(TEMPERATURE_TAG, currentWeather.temperature).apply();
        preferences.edit().putString(DESCRIPTION_TAG, currentWeather.description).apply();
        preferences.edit().putString(HUMIDITY_TAG, currentWeather.humidity).apply();
        preferences.edit().putString(MIN_TEMP_TAG, currentWeather.tempMin).apply();
        preferences.edit().putString(MAX_TEMP_TAG, currentWeather.tempMax).apply();
        preferences.edit().putString(WIND_TAG, currentWeather.wind).apply();
    }
}
