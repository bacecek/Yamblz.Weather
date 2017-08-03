package me.grechka.yamblz.yamblzweatherapp.presentation.settings;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import me.grechka.yamblz.yamblzweatherapp.background.WeatherJobUtils;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.data.storages.PrefsStorage;
import me.grechka.yamblz.yamblzweatherapp.data.storages.Storage;
import me.grechka.yamblz.yamblzweatherapp.di.modules.DataModule;

/**
 * Created by Grechka on 14.07.2017.
 */

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    private AppRepository repository;
    private WeatherJobUtils weatherJobUtils;

    @Inject
    public SettingsPresenter(@NonNull AppRepository repository,
                             @NonNull WeatherJobUtils weatherJobUtils) {
        this.repository = repository;
        this.weatherJobUtils = weatherJobUtils;
    }

    void changeUpdateSchedule(int minutes) {
        repository.setFrequency(minutes);
        weatherJobUtils.rescheduleWeatherJob(minutes);
    }

    String getUpdateFrequency() {
        return String.valueOf(repository.getFrequency());
    }

}