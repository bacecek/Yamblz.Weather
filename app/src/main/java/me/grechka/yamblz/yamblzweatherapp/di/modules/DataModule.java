package me.grechka.yamblz.yamblzweatherapp.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.grechka.yamblz.yamblzweatherapp.data.database.AppDatabase;
import me.grechka.yamblz.yamblzweatherapp.data.storages.PrefsStorage;
import me.grechka.yamblz.yamblzweatherapp.data.storages.Storage;
import me.grechka.yamblz.yamblzweatherapp.domain.WeatherInteractor;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepositoryImpl;
import me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi;

/**
 * Created by Grechka on 19.07.2017.
 */

@Module
public class DataModule {

    public static final String DATABASE_KEY = "keys.database";
    public static final String PREFS_STORAGE_KEY = "keys.storage.prefs";

    @Provides
    @NonNull
    @Singleton
    public AppRepository provideRepository(AppDatabase database,
                                           @Named(DataModule.PREFS_STORAGE_KEY) Storage<String> preferences,
                                           WeatherApi weatherApi,
                                           SuggestApi suggestApi) {
        return new AppRepositoryImpl(database, preferences, weatherApi, suggestApi);
    }

    @Provides
    @NonNull
    @Singleton
    @Named(PREFS_STORAGE_KEY)
    public Storage<String> providePrefs(Context context) {
        return new PrefsStorage(context);
    }

    @Provides
    @NonNull
    @Singleton
    public AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_KEY).build();
    }
}
