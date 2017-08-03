package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import java.util.Date;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.R;

/**
 * Created by alexander on 14/07/2017.
 */

public class ClearSky implements WeatherType {

    @Override
    public boolean isApplicable(Weather weather) {
        long currentTime = new Date().getTime();
        boolean timeCondition =
                currentTime < weather.getSunRiseTime() || currentTime >= weather.getSunSetTime();
        return timeCondition && weather.getWeatherId() == 800;
    }

    @Override
    public int getIconRes() {
        return R.string.all_weather_clear_night_icon;
    }

    @Override
    public int getTitleRes() {
        return R.string.all_weather_clear_night_title;
    }

    @Override
    public int getCardColor() {
        return R.color.colorClearNightCard;
    }

    @Override
    public int getTextColor() {
        return R.color.colorClearNightText;
    }
}
