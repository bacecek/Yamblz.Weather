package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.models.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.repository.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by Grechka on 15.07.2017.
 */

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    public final String NO_INFORMATION = "-";

    private AppRepository appRepository;
    private RxSchedulers scheduler;

    @Inject
    public WeatherPresenter(@NonNull RxSchedulers scheduler,
                            @NonNull AppRepository appRepository) {
        this.scheduler = scheduler;
        this.appRepository = appRepository;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        updateCity();
    }

    void updateCity() {
        getViewState().showCity(appRepository.getCity());
    }

    void updateCurrentWeather() {
        appRepository
                .updateCurrentWeather()
                .compose(scheduler.getIoToMainTransformerSingle())
                .subscribe(this::showCurrentWeather);
    }

    private void showCurrentWeather(CurrentWeather currentWeather) {
        getViewState().showCurrentWeather(currentWeather.temperature,
                currentWeather.description,
                currentWeather.humidity,
                currentWeather.tempMin,
                currentWeather.tempMax,
                currentWeather.wind);
    }

    void showSavedCurrentWeather() {
        appRepository.getSavedCurrentWeather()
                .subscribe(weather -> {
                    if (weather.temperature.compareTo(NO_INFORMATION) == 0) updateCurrentWeather();
                    else showCurrentWeather(weather);
                });
    }
}