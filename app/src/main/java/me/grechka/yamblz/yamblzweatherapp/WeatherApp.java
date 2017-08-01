package me.grechka.yamblz.yamblzweatherapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.di.AppComponent;
import me.grechka.yamblz.yamblzweatherapp.di.modules.AppModule;
import me.grechka.yamblz.yamblzweatherapp.di.DaggerAppComponent;
import me.grechka.yamblz.yamblzweatherapp.di.modules.DataModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.JobModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.NetworkModule;
import me.grechka.yamblz.yamblzweatherapp.data.prefs.PreferencesManager;
import me.grechka.yamblz.yamblzweatherapp.background.WeatherJobUtils;

/**
 * Created by Grechka on 14.07.2017.
 */
public class WeatherApp extends Application {

    @Inject WeatherJobUtils weatherJobUtils;
    @Inject PreferencesManager preferencesManager;

    private AppComponent appComponent;

    public static WeatherApp get(@NonNull Context context) {
        return (WeatherApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildDi();
        appComponent.inject(this);

        onSchedule();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent buildDi() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .jobModule(new JobModule())
                .networkModule(new NetworkModule())
                .dataModule(new DataModule())
                .build();
    }

    void onSchedule() {
        int minutes = Integer.parseInt(preferencesManager.getUpdateFrequency());
        weatherJobUtils.scheduleWeatherJob(minutes);
    }
}