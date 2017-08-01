package me.grechka.yamblz.yamblzweatherapp.di;

import javax.inject.Singleton;

import dagger.Component;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.di.modules.AppModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.DataModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.JobModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.NetworkModule;
import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainPresenter;
import me.grechka.yamblz.yamblzweatherapp.interactor.InteractorImp;
import me.grechka.yamblz.yamblzweatherapp.presentation.citySearch.CitySearchFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.settings.SettingsFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.settings.SettingsPresenter;
import me.grechka.yamblz.yamblzweatherapp.schedule.CurrentWeatherUpdateJob;
import me.grechka.yamblz.yamblzweatherapp.schedule.WeatherJobUtils;
import me.grechka.yamblz.yamblzweatherapp.presentation.weather.WeatherFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.weather.WeatherPresenter;

/**
 * Created by Grechka on 19.07.2017.
 */

@Component(modules = {AppModule.class, JobModule.class, NetworkModule.class, DataModule.class })
@Singleton
public interface AppComponent {

    void inject(WeatherApp weatherApp);

    void inject(WeatherFragment weatherFragment);
    void inject(WeatherPresenter weatherPresenter);

    void inject(SettingsFragment settingsFragment);
    void inject(SettingsPresenter settingsPresenter);

    void inject(CitySearchFragment fragment);

    void inject(CurrentWeatherUpdateJob currentWeatherUpdateJob);
    void inject(WeatherJobUtils weatherJobUtils);

    void inject(InteractorImp interactorImp);

    MainPresenter getMainPresenter();
    WeatherPresenter getWeatherPresenter();
    SettingsPresenter getSettingsPresenter();
}
