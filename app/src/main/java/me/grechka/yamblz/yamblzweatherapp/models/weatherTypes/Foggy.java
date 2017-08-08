package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;


import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.R;

/**
 * Created by alexander on 14/07/2017.
 */

public class Foggy implements WeatherType {

    @Override
    public boolean isApplicable(Weather weather) {
        return weather.getWeatherId() / 100 == 7;
    }

    @Override
    public int getIconRes() {
        return R.string.all_weather_foggy_icon;
    }

    @Override
    public int getTitleRes() {
        return R.string.all_weather_foggy_title;
    }

    @Override
    public int getCardColor() {
        return R.color.colorFoggyCard;
    }

    @Override
    public int getTextColor() {
        return R.color.colorFoggyText;
    }
}
