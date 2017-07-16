package me.grechka.yamblz.yamblzweatherapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import me.grechka.yamblz.yamblzweatherapp.AboutFragment;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.settings.SettingsFragment;
import me.grechka.yamblz.yamblzweatherapp.updating.WeatherUpdateJob;
import me.grechka.yamblz.yamblzweatherapp.weather.WeatherFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JobManager mJobManager = JobManager.instance();
        if (savedInstanceState == null)
            showWeather();
        if (mJobManager.getAllJobRequestsForTag(WeatherUpdateJob.TAG).isEmpty())
            mJobManager.schedule(new JobRequest.Builder(WeatherUpdateJob.TAG)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setPeriodic(Integer.parseInt(getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("freq", "60"))*60000)
                    .setPersisted(true)
                    .build());
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ((drawer != null) && (drawer.isDrawerOpen(GravityCompat.START)))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void showWeather() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new WeatherFragment())
                .commitAllowingStateLoss();
    }

    @Override
    public void showSettings() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void showAbout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new AboutFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void navigate(int id) {
        if (id == R.id.nav_settings)
            presenter.showSettings();
        else if (id == R.id.nav_about)
            presenter.showAbout();
    }

}
