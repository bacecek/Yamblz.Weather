package me.grechka.yamblz.yamblzweatherapp.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepositoryImpl;
import me.grechka.yamblz.yamblzweatherapp.data.NetworkRepository;
import me.grechka.yamblz.yamblzweatherapp.data.NetworkRepositoryImpl;
import me.grechka.yamblz.yamblzweatherapp.data.PreferencesRepository;
import me.grechka.yamblz.yamblzweatherapp.data.PreferencesRepositoryImpl;
import me.grechka.yamblz.yamblzweatherapp.data.database.AppDatabase;
import me.grechka.yamblz.yamblzweatherapp.data.storages.PrefsStorage;
import me.grechka.yamblz.yamblzweatherapp.data.storages.Storage;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;
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
    public NetworkRepository provideNetworkRepository(WeatherApi weatherApi,
                                                      SuggestApi suggestApi) {
        return new NetworkRepositoryImpl(weatherApi, suggestApi);
    }

    @Provides
    @NonNull
    @Singleton
    public PreferencesRepository providePreferencesRepository(@Named(DataModule.PREFS_STORAGE_KEY) Storage<String> preferences) {
        return new PreferencesRepositoryImpl(preferences);
    }

    @Provides
    @NonNull
    @Singleton
    public DatabaseRepository provideDatabaseRepository(AppDatabase appDatabase,
                                                        NetworkRepository networkRepository) {
        return new DatabaseRepositoryImpl(appDatabase, networkRepository);
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
