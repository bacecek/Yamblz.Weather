package me.grechka.yamblz.yamblzweatherapp.background;

import android.support.annotation.NonNull;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.WeatherApp;

/**
 * Created by Grechka on 19.07.2017.
 */

public class WeatherJobUtils {

    private JobManager jobManager;

    @Inject
    public WeatherJobUtils(@NonNull JobManager jobManager) {
        this.jobManager = jobManager;
    }

    public void scheduleWeatherJob(int minutes) {
        if ((minutes > 0) && (jobManager.getAllJobRequestsForTag(CurrentWeatherUpdateJob.TAG).isEmpty()))
            jobManager.schedule(new JobRequest.Builder(CurrentWeatherUpdateJob.TAG)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setPeriodic(minutes * 1000 * 60)
                    .build());
    }

    public void rescheduleWeatherJob(int minutes) {
        jobManager.cancelAllForTag(CurrentWeatherUpdateJob.TAG);
        scheduleWeatherJob(minutes);
    }
}
