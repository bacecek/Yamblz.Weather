package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by alexander on 14/07/2017.
 */

public interface WeatherType {
    boolean isApplicable(Weather weather);

    @StringRes int getIconRes();
    @StringRes int getTitleRes();
    @ColorRes int getCardColor();
    @ColorRes int getTextColor();
}
