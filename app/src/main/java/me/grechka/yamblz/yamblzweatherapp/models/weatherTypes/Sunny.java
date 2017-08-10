package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import android.support.annotation.NonNull;

import java.util.Date;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.R;

/**
 * Created by alexander on 14/07/2017.
 */

public class Sunny implements WeatherType {

    @Override
    public boolean isApplicable(Weather weather) {
        if (weather.getWeatherId() != 800) return false;
        if (!isScheduled(weather)) return true;

        long currentTime = new Date().getTime();

        boolean timeCondition =
                currentTime >= weather.getSunRiseTime() && currentTime < weather.getSunSetTime();

        return timeCondition;
    }

    @Override
    public int getIconRes() {
        return R.string.all_weather_sunny_icon;
    }

    @Override
    public int getTitleRes() {
        return R.string.all_weather_sunny_title;
    }

    @Override
    public int getCardColor() {
        return R.color.colorSunnyCard;
    }

    @Override
    public int getTextColor() {
        return R.color.colorSunnyText;
    }

    private boolean isScheduled(@NonNull Weather weather) {
        return !(weather.getSunRiseTime() == Weather.NO_SUN_PERIODIC ||
                weather.getSunSetTime() == Weather.NO_SUN_PERIODIC);
    }
}
