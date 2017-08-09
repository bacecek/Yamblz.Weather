package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.domain.weather.WeatherInteractor;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BasePresenter;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by Grechka on 15.07.2017.
 */

@MainScope
@InjectViewState
public class WeatherPresenter extends BasePresenter<WeatherView> {

    private RxSchedulers scheduler;
    private List<Integer> unitMods;
    private WeatherInteractor interactor;
    private Set<WeatherType> weatherTypes;


    @Inject
    public WeatherPresenter(@NonNull RxSchedulers scheduler,
                            @NonNull WeatherInteractor interactor,
                            @NonNull Set<WeatherType> weatherTypes) {
        this.scheduler = scheduler;
        this.weatherTypes = weatherTypes;
        this.interactor = interactor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        addSubscription(interactor.getUnitsMods()
                .compose(scheduler.getIoToMainTransformer())
                .subscribe(this::setUnitMods));

        addSubscription(interactor.getCity()
                .compose(scheduler.getIoToMainTransformerFlowable())
                .subscribe(this::cityChanged));
    }

    private void setUnitMods(@NonNull List<Integer> unitMods) {
        this.unitMods = unitMods;
        this.getWeather();
    }

    private void cityChanged(@NonNull City city) {
        getViewState().showCity(city);
        this.getWeather();
    }

    void getWeather() {
        interactor.getWeather()
                .compose(scheduler.getIoToMainTransformerSingle())
                .subscribe(this::setWeather);
    }

    void updateWeather() {
        interactor
                .updateWeather()
                .compose(scheduler.getIoToMainTransformerSingle())
                .subscribe(this::setWeather);
    }

    void getForecast() {
        interactor.getForecast()
                .compose(scheduler.getIoToMainTransformerSingle())
                .subscribe(getViewState()::addForecast);
    }

    boolean isCelsius() {
        return unitMods.get(0) == ConvertersConfig.TEMPERATURE_CELSIUS;
    }

    boolean isMmHg() {
        return unitMods.get(1) == ConvertersConfig.PRESSURE_MMHG;
    }

    boolean isMs() {
        return unitMods.get(2) == ConvertersConfig.SPEED_MS;
    }

    private void setWeather(@NonNull Weather weather) {
        for(WeatherType type: weatherTypes) {
            if (!type.isApplicable(weather)) continue;
            getViewState().setWeather(weather, type);
            break;
        }
        this.getForecast();
    }
}