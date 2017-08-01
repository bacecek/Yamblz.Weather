package me.grechka.yamblz.yamblzweatherapp.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.models.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.models.response.CurrentWeatherResponse;
import me.grechka.yamblz.yamblzweatherapp.models.response.Wind;

/**
 * Created by Grechka on 19.07.2017.
 */

public class InteractorImp implements Interactor {

    Context context;

    public InteractorImp(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public CurrentWeather getCurrentWeatherFromResponse(CurrentWeatherResponse response) {
        String temperature = roundDoubleToString(response.getWeatherInfo().getTemp());
        String description = response.getWeather().get(0).getDescription();
        String humidity = Integer.toString(response.getWeatherInfo().getHumidity());
        String tempMin = roundDoubleToString(response.getWeatherInfo().getTempMin());
        String tempMax = roundDoubleToString(response.getWeatherInfo().getTempMax());
        String wind = buildWindString(response.getWind());
        return new CurrentWeather(temperature, description, humidity, tempMin, tempMax, wind);
    }

    private String roundDoubleToString(Double param) {
        return Long.toString(Math.round(param));
    }

    @NonNull
    private String buildWindString(Wind windResponse) {
        StringBuilder windBuilder = new StringBuilder();
        if (windResponse.getSpeed() >= 0.5) {
            windBuilder.append(roundDoubleToString(windResponse.getSpeed()));
            windBuilder.append(" м/с, ");
            windBuilder.append(windDegtoDirection(windResponse.getDeg()));
            return windBuilder.toString();
        } else
            return "штиль";
    }

    @NonNull
    private String windDegtoDirection(double deg) {
        if (deg < 13.0) return context.getResources().getString(R.string.north);
        else if (deg < 35.0) return context.getResources().getString(R.string.north_north_east);
        else if (deg < 58.0) return context.getResources().getString(R.string.north_east);
        else if (deg < 80.0) return context.getResources().getString(R.string.east_north_east);
        else if (deg < 103.0) return context.getResources().getString(R.string.east);
        else if (deg < 125.0) return context.getResources().getString(R.string.east_south_east);
        else if (deg < 148.0) return context.getResources().getString(R.string.south_east);
        else if (deg < 170.0) return context.getResources().getString(R.string.south_south_east);
        else if (deg < 193.0) return context.getResources().getString(R.string.south);
        else if (deg < 215.0) return context.getResources().getString(R.string.south_south_west);
        else if (deg < 238.0) return context.getResources().getString(R.string.south_west);
        else if (deg < 260.0) return context.getResources().getString(R.string.west_south_west);
        else if (deg < 283.0) return context.getResources().getString(R.string.west);
        else if (deg < 305.0) return context.getResources().getString(R.string.west_north_west);
        else if (deg < 328.0) return context.getResources().getString(R.string.north_west);
        else if (deg < 350.0) return context.getResources().getString(R.string.north_north_west);
        else return context.getResources().getString(R.string.north);
    }
}
