package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import me.grechka.yamblz.yamblzweatherapp.models.City;

/**
 * Created by Grechka on 15.07.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void setCityToHeader(@NonNull City city);

    @StateStrategyType(SkipStrategy.class) void showWeather();
    @StateStrategyType(SkipStrategy.class) void showSettings();
    @StateStrategyType(SkipStrategy.class) void showAbout();
    @StateStrategyType(SkipStrategy.class) void showFavorites();
    @StateStrategyType(SkipStrategy.class) void navigate(int id);
    @StateStrategyType(SkipStrategy.class) void goBack();
}
