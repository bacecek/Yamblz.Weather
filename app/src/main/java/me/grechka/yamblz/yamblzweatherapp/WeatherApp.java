package me.grechka.yamblz.yamblzweatherapp;

import android.app.Application;

import com.arellomobile.mvp.MvpFacade;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.di.AppComponent;
import me.grechka.yamblz.yamblzweatherapp.di.modules.AppModule;
import me.grechka.yamblz.yamblzweatherapp.di.DaggerAppComponent;
import me.grechka.yamblz.yamblzweatherapp.di.modules.DataModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.JobModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.NetworkModule;
import me.grechka.yamblz.yamblzweatherapp.repository.prefs.PreferencesManager;
import me.grechka.yamblz.yamblzweatherapp.schedule.WeatherJobUtils;

/**
 * Created by Grechka on 14.07.2017.
 */

public class WeatherApp extends Application {
    private static AppComponent component;

    @Inject
    PreferencesManager preferencesManager;
    @Inject
    WeatherJobUtils weatherJobUtils;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
        component.inject(this);
        MvpFacade.init();
        setUpdateSchedule();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .jobModule(new JobModule())
                .networkModule(new NetworkModule())
                .dataModule(new DataModule())
                .build();
    }

    void setUpdateSchedule() {
        int minutes = Integer.parseInt(preferencesManager.getUpdateFrequency());
        weatherJobUtils.scheduleWeatherJob(minutes);
    }
}