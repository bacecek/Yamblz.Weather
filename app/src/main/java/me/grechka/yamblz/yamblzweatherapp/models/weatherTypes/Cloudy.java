package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.R;

/**
 * Created by alexander on 14/07/2017.
 */

public class Cloudy implements WeatherType {

    @Override
    public boolean isApplicable(Weather weather) {
        return weather.getWeatherId() != 800 && weather.getWeatherId() / 100 == 8;
    }

    @Override
    public int getIconRes() {
        return R.string.all_weather_cloudy_icon;
    }

    @Override
    public int getTitleRes() {
        return R.string.all_weather_cloudy_title;
    }

    @Override
    public int getCardColor() {
        return R.color.colorCloudyCard;
    }

    @Override
    public int getTextColor() {
        return R.color.colorCloudyText;
    }
}
