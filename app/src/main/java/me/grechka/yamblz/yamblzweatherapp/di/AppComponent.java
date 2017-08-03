package me.grechka.yamblz.yamblzweatherapp.di;

import javax.inject.Singleton;

import dagger.Component;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.di.modules.AppModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.DataModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.JobModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.NetworkModule;
import me.grechka.yamblz.yamblzweatherapp.domain.WeatherInteractorImpl;
import me.grechka.yamblz.yamblzweatherapp.background.CurrentWeatherUpdateJob;
import me.grechka.yamblz.yamblzweatherapp.background.WeatherJobUtils;

/**
 * Created by Grechka on 19.07.2017.
 */

@Singleton
@Component(modules = {AppModule.class, JobModule.class, NetworkModule.class, DataModule.class })
public interface AppComponent {

    void inject(WeatherApp weatherApp);

    void inject(CurrentWeatherUpdateJob currentWeatherUpdateJob);
    void inject(WeatherJobUtils weatherJobUtils);

    void inject(WeatherInteractorImpl interactorImp);

    MainComponent addMainComponent();
}
