package me.grechka.yamblz.yamblzweatherapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.grechka.yamblz.yamblzweatherapp.schedule.WeatherJobCreator;
import me.grechka.yamblz.yamblzweatherapp.schedule.WeatherJobUtils;

/**
 * Created by Grechka on 19.07.2017.
 */

@Module
public class JobModule {

    @Provides
    @NonNull
    @Singleton
    JobManager provideJobManager(Context context, WeatherJobCreator creator) {
        JobManager jobManager = JobManager.create(context);
        jobManager.addJobCreator(creator);
        return jobManager;
    }

    @Provides
    @NonNull
    @Singleton
    WeatherJobCreator provideWeatherJobCreator() {
        return new WeatherJobCreator();
    }

    @Provides
    @NonNull
    @Singleton
    WeatherJobUtils provideWeatherJobUtils() {
        return new WeatherJobUtils();
    }
}
