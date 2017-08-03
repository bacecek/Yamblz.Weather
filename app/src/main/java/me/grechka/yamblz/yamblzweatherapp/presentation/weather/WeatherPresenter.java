package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Set;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by Grechka on 15.07.2017.
 */

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private RxSchedulers scheduler;
    private AppRepository appRepository;
    private Set<WeatherType> weatherTypes;

    @Inject
    public WeatherPresenter(@NonNull RxSchedulers scheduler,
                            @NonNull AppRepository appRepository,
                            @NonNull Set<WeatherType> weatherTypes) {
        this.scheduler = scheduler;
        this.appRepository = appRepository;
        this.weatherTypes = weatherTypes;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        updateCity();
    }

    void updateCity() {
        appRepository.getCity()
                .subscribe(getViewState()::showCity);
    }

    void updateCurrentWeather() {
        appRepository
                .updateCurrentWeather()
                .compose(scheduler.getIoToMainTransformerSingle())
                .subscribe(this::setWeather);
    }

    void showSavedCurrentWeather() {
        appRepository.getSavedCurrentWeather()
                .onErrorResumeNext(t -> appRepository.updateCurrentWeather())
                .compose(scheduler.getIoToMainTransformerSingle())
                .subscribe(this::setWeather, t -> t.printStackTrace());
    }

    private void setWeather(@NonNull Weather weather) {
        for(WeatherType type: weatherTypes) {
            if (!type.isApplicable(weather)) continue;
            Log.d("Gere", type.toString());
            getViewState().setWeather(weather, type);
            break;
        }
    }
}