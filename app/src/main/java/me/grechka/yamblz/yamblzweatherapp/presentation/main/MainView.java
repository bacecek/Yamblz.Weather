package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import me.grechka.yamblz.yamblzweatherapp.models.City;

/**
 * Created by Grechka on 15.07.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void setCityToHeader(@NonNull City city);

    @StateStrategyType(SingleStateStrategy.class)
    void showWeather();
    void showSettings();
    void showAbout();

    @StateStrategyType(SkipStrategy.class)
    void navigate(int id);
    void goBack();

}
