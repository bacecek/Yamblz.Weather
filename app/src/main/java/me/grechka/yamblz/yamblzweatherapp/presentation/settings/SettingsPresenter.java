package me.grechka.yamblz.yamblzweatherapp.presentation.settings;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.data.prefs.PreferencesManager;
import me.grechka.yamblz.yamblzweatherapp.background.WeatherJobUtils;

/**
 * Created by Grechka on 14.07.2017.
 */

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    WeatherJobUtils weatherJobUtils;
    PreferencesManager preferencesManager;

    @Inject
    public SettingsPresenter(@NonNull WeatherJobUtils weatherJobUtils,
                             @NonNull PreferencesManager preferencesManager) {
        this.weatherJobUtils = weatherJobUtils;
        this.preferencesManager = preferencesManager;
    }

    void changeUpdateSchedule(int minutes) {
        preferencesManager.putUpdateFrequency(Integer.toString(minutes));
        weatherJobUtils.rescheduleWeatherJob(minutes);
    }

    String getUpdateFrequency() {
        return preferencesManager.getUpdateFrequency();
    }

}