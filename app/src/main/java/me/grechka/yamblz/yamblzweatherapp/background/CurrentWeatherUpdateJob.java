package me.grechka.yamblz.yamblzweatherapp.background;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;

/**
 * Created by Grechka on 14.07.2017.
 */

public class CurrentWeatherUpdateJob extends Job {

    static final String TAG = "job_update_current_weather";

    private AppRepository repository;

    @Inject
    CurrentWeatherUpdateJob(@NonNull AppRepository repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        Log.d("Job", "onRunJob: Job started");

        repository
                .getNetworkWeather()
                .subscribe(weather -> {
            Log.d("Job", "Job completed: " + weather.toString());
        });

        return Result.SUCCESS;
    }
}