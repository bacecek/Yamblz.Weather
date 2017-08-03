package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;

/**
 * Created by Grechka on 14.07.2017.
 */

public interface WeatherView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void setWeather(Weather weather, WeatherType type);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);

    void showCity(@NonNull City city);
}
