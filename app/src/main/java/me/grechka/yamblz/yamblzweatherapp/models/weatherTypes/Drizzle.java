package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by alexander on 14/07/2017.
 */

public class Drizzle implements WeatherType {

    @Override
    public boolean isApplicable(Weather weather) {
        return weather.getWeatherId() / 100 == 3;
    }

    @Override
    public int getIconRes() {
        return R.string.all_weather_drizzle_icon;
    }

    @Override
    public int getTitleRes() {
        return R.string.all_weather_drizzle_title;
    }

    @Override
    public int getCardColor() {
        return R.color.colorDrizzleCard;
    }

    @Override
    public int getTextColor() {
        return R.color.colorDrizzleText;
    }
}
