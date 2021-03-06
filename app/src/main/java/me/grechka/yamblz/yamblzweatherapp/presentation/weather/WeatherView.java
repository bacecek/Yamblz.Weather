package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;

/**
 * Created by Grechka on 14.07.2017.
 */

public interface WeatherView extends MvpView {
    void addForecasts(List<Weather> forecast);

    @StateStrategyType(SkipStrategy.class) void onMissingCityError();
    void setErrorViewEnabled(boolean isEnabled);

    void setWeather(Weather weather, WeatherType type);
    void showCity(@NonNull City city);

    void hideLoading();
}
