package me.grechka.yamblz.yamblzweatherapp.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by alexander on 03/08/2017.
 */

@Entity(tableName = "forecasts")
public class ForecastEntity {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "weather_id")
    private int weatherId;

    @ColumnInfo(name = "temperature")
    private float temperature;

    @ColumnInfo(name = "date")
    private long date;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public static Weather toWeather(@NonNull ForecastEntity entity) {
        return new Weather.Builder()
                .weatherId(entity.getWeatherId())
                .temperature(entity.getTemperature())
                .updateTime(entity.getDate())
                .build();
    }

    public static ForecastEntity fromWeather(@NonNull Weather weather,
                                            @NonNull CityEntity city) {
        ForecastEntity entity = new ForecastEntity();
        entity.setWeatherId(weather.getWeatherId());
        entity.setCityId(city.getUid());
        entity.setTemperature(weather.getTemperature());
        entity.setDate(weather.getUpdateTime());
        return entity;
    }
}
