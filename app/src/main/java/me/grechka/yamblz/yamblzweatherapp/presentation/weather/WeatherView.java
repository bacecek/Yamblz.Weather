package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Grechka on 14.07.2017.
 */

public interface WeatherView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showCurrentWeather(String temperature,
                            String description,
                            String humidity,
                            String tempMin,
                            String tempMax,
                            String wind);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void closeDrawer();

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);

    void showCityTitle(@NonNull String title);
}
