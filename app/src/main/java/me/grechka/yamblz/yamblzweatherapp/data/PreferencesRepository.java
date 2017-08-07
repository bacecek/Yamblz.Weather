package me.grechka.yamblz.yamblzweatherapp.data;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by alexander on 07/08/2017.
 */

public interface PreferencesRepository {
    String UNITS_TEMPERATURE_PREFS_KEY = "app.prefs.temperature.units";
    String UNITS_PRESSURE_PREFS_KEY = "app.prefs.pressure.units";
    String UNITS_SPEED_PREFS_KEY = "app.prefs.speed.units";
    String FREQUENCY_KEY = "keys.frequency";

    Observable<List<Integer>> getUnits();

    void setFrequency(int frequency);
    int getFrequency();

    void saveTemperatureUnits(int units);
    int getTemperatureUnits();

    void savePressureUnits(int units);
    int getPressureUnits();

    void saveSpeedUnits(int units);
    int getSpeedUnits();
}
