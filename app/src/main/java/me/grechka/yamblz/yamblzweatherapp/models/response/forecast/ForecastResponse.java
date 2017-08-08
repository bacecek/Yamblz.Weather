package me.grechka.yamblz.yamblzweatherapp.models.response.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.grechka.yamblz.yamblzweatherapp.models.response.weather.WeatherResponse;

/**
 * Created by alexander on 03/08/2017.
 */

public class ForecastResponse {

    @SerializedName("cnt")
    @Expose
    private int count;

    @SerializedName("list")
    @Expose
    private List<Forecast> forecastList;

    public int getCount() {
        return count;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ForecastResponse{");
        sb.append("count=").append(count);
        sb.append(", forecastList=").append(forecastList);
        sb.append('}');
        return sb.toString();
    }
}
