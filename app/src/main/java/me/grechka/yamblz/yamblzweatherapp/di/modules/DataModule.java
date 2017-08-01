package me.grechka.yamblz.yamblzweatherapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.grechka.yamblz.yamblzweatherapp.domain.Interactor;
import me.grechka.yamblz.yamblzweatherapp.domain.InteractorImp;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;
import me.grechka.yamblz.yamblzweatherapp.data.prefs.PreferencesManager;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepositoryImp;
import me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi;

/**
 * Created by Grechka on 19.07.2017.
 */

@Module
public class DataModule {

    @Provides
    @NonNull
    @Singleton
    public AppRepository provideRepository(Interactor interactor,
                                           WeatherApi weatherApi,
                                           SuggestApi suggestApi,
                                           PreferencesManager preferencesManager) {
        return new AppRepositoryImp(interactor, weatherApi, suggestApi, preferencesManager);
    }

    @Provides
    @NonNull
    @Singleton
    public PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }

    @Provides
    @NonNull
    @Singleton
    public Interactor provideInteractor(Context context) {
        return new InteractorImp(context);
    }
}
