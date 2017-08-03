package me.grechka.yamblz.yamblzweatherapp.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;
import android.support.v4.util.TimeUtils;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by alexander on 02/08/2017.
 */

@Entity(tableName = "weather")
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "weather_id")
    private int weatherId;

    @ColumnInfo(name = "temperature")
    private float temperature;

    @ColumnInfo(name = "min_temperature")
    private float minTemperature;

    @ColumnInfo(name = "max_temperature")
    private float maxTemperature;

    @ColumnInfo(name = "humidity")
    private float humidity;

    @ColumnInfo(name = "wind_speed")
    private float windSpeed;

    @ColumnInfo(name = "pressure")
    private float pressure;

    @ColumnInfo(name = "sunrise")
    private long sunrise;

    @ColumnInfo(name = "sunset")
    private long sunset;

    @ColumnInfo(name = "forecast")
    private boolean isForecast = false;

    @ColumnInfo(name = "city")
    private long cityId;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public boolean isForecast() {
        return isForecast;
    }

    public void setForecast(boolean forecast) {
        isForecast = forecast;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public static Weather toWeather(@NonNull WeatherEntity entity) {
        return new Weather.Builder()
                .weatherId(entity.getWeatherId())
                .minTemperature(entity.getMinTemperature())
                .maxTemperature(entity.getMaxTemperature())
                .temperature(entity.getTemperature())
                .humidity(entity.getHumidity())
                .pressure(entity.getPressure())
                .windSpeed(entity.getWindSpeed())
                .sunSetTime(entity.getSunset())
                .sunRiseTime(entity.getSunrise())
                .build();
    }

    public static WeatherEntity fromWeather(@NonNull Weather weather,
                                      @NonNull CityEntity city,
                                      boolean isForecast) {
        WeatherEntity entity = new WeatherEntity();
        entity.setWeatherId(weather.getWeatherId());
        entity.setCityId(city.getUid());
        entity.setForecast(isForecast);
        entity.setHumidity(weather.getHumidity());
        entity.setMaxTemperature(weather.getMaxTemperature());
        entity.setMinTemperature(weather.getMinTemperature());
        entity.setTemperature(weather.getTemperature());
        entity.setPressure(weather.getPressure());
        entity.setWindSpeed(weather.getWindSpeed());
        entity.setSunrise(weather.getSunRiseTime());
        entity.setSunset(weather.getSunSetTime());
        return entity;
    }
}
