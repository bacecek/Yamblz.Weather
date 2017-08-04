package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Set;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.domain.WeatherInteractor;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by Grechka on 15.07.2017.
 */

@MainScope
@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private RxSchedulers scheduler;
    private Set<WeatherType> weatherTypes;
    private WeatherInteractor interactor;

    @Inject
    public WeatherPresenter(@NonNull RxSchedulers scheduler,
                            @NonNull WeatherInteractor interactor,
                            @NonNull Set<WeatherType> weatherTypes) {
        this.scheduler = scheduler;
        this.weatherTypes = weatherTypes;
        this.interactor = interactor;

        updateCity();
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);

        //getWeather();
        //getForecast();
    }

    void updateCity() {
        interactor.getCity()
                .subscribe(city -> {
                    getViewState().showCity(city);
                    this.updateWeather();
                });
    }

    void updateWeather() {
        interactor
                .updateWeather()
                .compose(scheduler.getIoToMainTransformerSingle())
                .subscribe(this::setWeather);
    }

    void getWeather() {
        interactor.getCachedWeather()
//                .onErrorResumeNext(t -> interactor.updateWeather())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setWeather, t -> t.printStackTrace());
    }

    void getForecast() {
        getViewState().clearForecast();

        interactor.getForecast()
                .compose(scheduler.getIoToMainTransformer())
                .subscribe(getViewState()::addForecast);
    }

    boolean isCelsius() {
        return interactor.getModes()[0] == ConvertersConfig.TEMPERATURE_CELSIUS;
    }

    boolean isMmHg() {
        return interactor.getModes()[1] == ConvertersConfig.PRESSURE_MMHG;
    }

    boolean isMs() {
        return interactor.getModes()[2] == ConvertersConfig.SPEED_MS;
    }

    private void setWeather(@NonNull Weather weather) {
        for(WeatherType type: weatherTypes) {
            if (!type.isApplicable(weather)) continue;
            getViewState().setWeather(weather, type);
            break;
        }
    }
}