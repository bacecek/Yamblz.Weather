package me.grechka.yamblz.yamblzweatherapp.di.modules;

import dagger.Binds;
import dagger.Module;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.domain.settings.SettingsInteractor;
import me.grechka.yamblz.yamblzweatherapp.domain.settings.SettingsInteractorImpl;
import me.grechka.yamblz.yamblzweatherapp.domain.weather.WeatherInteractor;
import me.grechka.yamblz.yamblzweatherapp.domain.weather.WeatherInteractorImpl;

/**
 * Created by alexander on 03/08/2017.
 */

@Module
public abstract class BindsModule {

    @Binds
    @MainScope
    public abstract WeatherInteractor provideWeatherInteractor(WeatherInteractorImpl impl);

    @Binds
    @MainScope
    public abstract SettingsInteractor provideSettingsInteractor(SettingsInteractorImpl impl);
}
