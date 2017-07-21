package me.grechka.yamblz.yamblzweatherapp.interactor;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.interactor.Interactor;
import me.grechka.yamblz.yamblzweatherapp.model.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.model.response.CurrentWeatherResponse;
import me.grechka.yamblz.yamblzweatherapp.model.response.Wind;

/**
 * Created by Grechka on 19.07.2017.
 */

public class InteractorImp implements Interactor {

    @Inject
    Context context;

    public InteractorImp() {
        WeatherApp.getComponent().inject(this);
    }

    @Override
    public CurrentWeather getCurrentWeatherFromResponse(CurrentWeatherResponse response) {
        String temperature = roundDoubletoString(response.getMain().getTemp());
        String description = response.getWeather().get(0).getDescription();
        String humidity = Integer.toString(response.getMain().getHumidity());
        String tempMin = roundDoubletoString(response.getMain().getTempMin());
        String tempMax = roundDoubletoString(response.getMain().getTempMax());
        String wind = buildWindString(response.getWind());
        return new CurrentWeather(temperature, description, humidity, tempMin, tempMax, wind);
    }

    private String roundDoubletoString(Double param) {
        return Long.toString(Math.round(param));
    }

    @NonNull
    private String buildWindString(Wind windResponse) {
        StringBuilder windBuilder = new StringBuilder();
        if (windResponse.getSpeed() >= 0.5) {
            windBuilder.append(roundDoubletoString(windResponse.getSpeed()));
            windBuilder.append(" м/с, ");
            windBuilder.append(windDegtoDirection(windResponse.getDeg()));
            return windBuilder.toString();
        } else
            return "штиль";
    }

    @NonNull
    private String windDegtoDirection(int deg) {
        if (deg < 13) return context.getResources().getString(R.string.north);
        else if (deg < 35) return context.getResources().getString(R.string.north_north_east);
        else if (deg < 58) return context.getResources().getString(R.string.north_east);
        else if (deg < 80) return context.getResources().getString(R.string.east_north_east);
        else if (deg < 103) return context.getResources().getString(R.string.east);
        else if (deg < 125) return context.getResources().getString(R.string.east_south_east);
        else if (deg < 148) return context.getResources().getString(R.string.south_east);
        else if (deg < 170) return context.getResources().getString(R.string.south_south_east);
        else if (deg < 193) return context.getResources().getString(R.string.south);
        else if (deg < 215) return context.getResources().getString(R.string.south_south_west);
        else if (deg < 238) return context.getResources().getString(R.string.south_west);
        else if (deg < 260) return context.getResources().getString(R.string.west_south_west);
        else if (deg < 283) return context.getResources().getString(R.string.west);
        else if (deg < 305) return context.getResources().getString(R.string.west_north_west);
        else if (deg < 328) return context.getResources().getString(R.string.north_west);
        else if (deg < 350) return context.getResources().getString(R.string.north_north_west);
        else return context.getResources().getString(R.string.north);
    }
}