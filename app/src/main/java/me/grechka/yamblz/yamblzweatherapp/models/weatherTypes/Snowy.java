package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.R;

/**
 * Created by alexander on 14/07/2017.
 */

public class Snowy implements WeatherType {

    @Override
    public boolean isApplicable(Weather weather) {
        return weather.getWeatherId() / 100 == 6;
    }

    @Override
    public int getIconRes() {
        return R.string.all_weather_snowy_icon;
    }

    @Override
    public int getTitleRes() {
        return R.string.all_weather_snowy_title;
    }

    @Override
    public int getCardColor() {
        return R.color.colorSnowyCard;
    }

    @Override
    public int getTextColor() {
        return R.color.colorSnowyText;
    }
}
