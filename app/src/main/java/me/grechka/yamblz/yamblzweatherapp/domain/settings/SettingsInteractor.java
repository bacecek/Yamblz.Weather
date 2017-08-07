package me.grechka.yamblz.yamblzweatherapp.domain.settings;

/**
 * Created by alexander on 07/08/2017.
 */

public interface SettingsInteractor {

    int getUpdateFrequency();
    void setUpdateFrequency(int frequency);

    int getTemperatureUnits();
    void setTemperatureUnits(int units);

    int getSpeedUnits();
    void setSpeedUnits(int units);

    int getPressureUnits();
    void setPressureUnits(int units);
}
