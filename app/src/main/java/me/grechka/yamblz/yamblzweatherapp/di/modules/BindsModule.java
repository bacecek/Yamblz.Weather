package me.grechka.yamblz.yamblzweatherapp.di.modules;

import dagger.Binds;
import dagger.Module;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.domain.WeatherInteractor;
import me.grechka.yamblz.yamblzweatherapp.domain.WeatherInteractorImpl;

/**
 * Created by alexander on 03/08/2017.
 */

@Module
public abstract class BindsModule {

    @Binds
    @MainScope
    public abstract WeatherInteractor providerWeatherInteractor(WeatherInteractorImpl impl);
}
