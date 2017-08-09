package me.grechka.yamblz.yamblzweatherapp.presentation.settings;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.background.WeatherJobUtils;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig;
import me.grechka.yamblz.yamblzweatherapp.domain.settings.SettingsInteractor;

/**
 * Created by Grechka on 14.07.2017.
 */

@MainScope
@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    private SettingsInteractor interactor;
    private WeatherJobUtils weatherJobUtils;

    @Inject
    public SettingsPresenter(@NonNull SettingsInteractor interactor,
                             @NonNull WeatherJobUtils weatherJobUtils) {
        this.interactor = interactor;
        this.weatherJobUtils = weatherJobUtils;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().highlightSettings();
    }

    void changeUpdateSchedule(int minutes) {
        interactor.setUpdateFrequency(minutes);
        weatherJobUtils.rescheduleWeatherJob(minutes);
    }

    public void saveTemperature(int id) {
        switch (id) {
            case R.id.content_common_settings_celsius:
                interactor.setTemperatureUnits(ConvertersConfig.TEMPERATURE_CELSIUS);
                break;
            case R.id.content_common_settings_fahrenheit:
                interactor.setTemperatureUnits(ConvertersConfig.TEMPERATURE_FAHRENHEIT);
                break;
        }
    }

    public void saveSpeed(int id) {
        switch (id) {
            case R.id.content_common_settings_ms:
                interactor.setSpeedUnits(ConvertersConfig.SPEED_MS);
                break;
            case R.id.content_common_settings_kmh:
                interactor.setSpeedUnits(ConvertersConfig.SPEED_KMH);
                break;
        }
    }

    public void savePressure(int id) {
        switch (id) {
            case R.id.content_common_settings_mmhg:
                interactor.setPressureUnits(ConvertersConfig.PRESSURE_MMHG);
                break;
            case R.id.content_common_settings_pascal:
                interactor.setPressureUnits(ConvertersConfig.PRESSURE_PASCAL);
                break;
        }
    }

    String getUpdateFrequency() {
        return String.valueOf(interactor.getUpdateFrequency());
    }

    public boolean isCelsius() {
        return interactor.getTemperatureUnits() == ConvertersConfig.TEMPERATURE_CELSIUS;
    }

    public boolean isMs() {
        return interactor.getSpeedUnits() == ConvertersConfig.SPEED_MS;
    }

    public boolean isMmHg() {
        return interactor.getPressureUnits() == ConvertersConfig.PRESSURE_MMHG;
    }
}