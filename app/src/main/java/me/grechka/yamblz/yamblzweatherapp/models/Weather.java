package me.grechka.yamblz.yamblzweatherapp.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

/**
 * Created by alexander on 14/07/2017.
 */

public class Weather {

    public static final long NO_SUN_PERIODIC = -1;

    @Expose
    private int weatherId;

    @Expose
    private float humidity;

    @Expose
    private float pressure;

    @Expose
    private float temperature;

    @Expose
    private float minTemperature;

    @Expose
    private float maxTemperature;

    @Expose
    private float windDegree;

    @Expose
    private float windSpeed;

    @Expose
    private int clouds;

    @Expose
    private long sunRiseTime;

    @Expose
    private long sunSetTime;

    @Expose
    private long updateTime;

    private Weather(@NonNull Builder builder) {
        this.weatherId = builder.weatherId;
        this.humidity = builder.humidity;
        this.pressure = builder.pressure;
        this.temperature = builder.temperature;
        this.minTemperature = builder.minTemperature;
        this.maxTemperature = builder.maxTemperature;
        this.windDegree = builder.windDegree;
        this.windSpeed = builder.windSpeed;
        this.clouds = builder.clouds;
        this.sunRiseTime = builder.sunRiseTime;
        this.sunSetTime = builder.sunSetTime;
        this.updateTime = builder.updateTime;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public float getWindDegree() {
        return windDegree;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public int getClouds() {
        return clouds;
    }

    public long getSunRiseTime() {
        return sunRiseTime;
    }

    public long getSunSetTime() {
        return sunSetTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        if (weatherId != weather.weatherId) return false;
        if (Float.compare(weather.humidity, humidity) != 0) return false;
        if (Float.compare(weather.pressure, pressure) != 0) return false;
        if (Float.compare(weather.temperature, temperature) != 0) return false;
        if (Float.compare(weather.minTemperature, minTemperature) != 0) return false;
        if (Float.compare(weather.maxTemperature, maxTemperature) != 0) return false;
        if (Float.compare(weather.windDegree, windDegree) != 0) return false;
        if (Float.compare(weather.windSpeed, windSpeed) != 0) return false;
        if (clouds != weather.clouds) return false;
        if (sunRiseTime != weather.sunRiseTime) return false;
        if (sunSetTime != weather.sunSetTime) return false;
        return updateTime == weather.updateTime;

    }

    @Override
    public int hashCode() {
        int result = weatherId;
        result = 31 * result + (humidity != +0.0f ? Float.floatToIntBits(humidity) : 0);
        result = 31 * result + (pressure != +0.0f ? Float.floatToIntBits(pressure) : 0);
        result = 31 * result + (temperature != +0.0f ? Float.floatToIntBits(temperature) : 0);
        result = 31 * result + (minTemperature != +0.0f ? Float.floatToIntBits(minTemperature) : 0);
        result = 31 * result + (maxTemperature != +0.0f ? Float.floatToIntBits(maxTemperature) : 0);
        result = 31 * result + (windDegree != +0.0f ? Float.floatToIntBits(windDegree) : 0);
        result = 31 * result + (windSpeed != +0.0f ? Float.floatToIntBits(windSpeed) : 0);
        result = 31 * result + clouds;
        result = 31 * result + (int) (sunRiseTime ^ (sunRiseTime >>> 32));
        result = 31 * result + (int) (sunSetTime ^ (sunSetTime >>> 32));
        result = 31 * result + (int) (updateTime ^ (updateTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Weather{");
        sb.append(", humidity='").append(humidity).append('\'');
        sb.append(", pressure='").append(pressure).append('\'');
        sb.append(", temperature=").append(temperature);
        sb.append(", minTemperature=").append(minTemperature);
        sb.append(", maxTemperature=").append(maxTemperature);
        sb.append(", windDegree=").append(windDegree);
        sb.append(", windSpeed=").append(windSpeed);
        sb.append(", clouds=").append(clouds);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private int weatherId;
        private float humidity;
        private float pressure;
        private float temperature;
        private float minTemperature;
        private float maxTemperature;
        private float windDegree;
        private float windSpeed;
        private int clouds;
        private long sunRiseTime = NO_SUN_PERIODIC;
        private long sunSetTime = NO_SUN_PERIODIC;
        private long updateTime;

        public Builder() {
        }

        public Builder(@NonNull Weather weather) {
            weatherId = weather.getWeatherId();
            humidity = weather.getHumidity();
            pressure = weather.getPressure();
            temperature = weather.getTemperature();
            minTemperature = weather.getMinTemperature();
            maxTemperature = weather.getMaxTemperature();
            windDegree = weather.getWindDegree();
            windSpeed = weather.getWindSpeed();
            clouds = weather.getClouds();
            sunRiseTime = weather.getSunRiseTime();
            sunSetTime = weather.getSunSetTime();
            updateTime = weather.getUpdateTime();
        }

        public Builder weatherId(int weatherId) {
            this.weatherId = weatherId;
            return this;
        }

        public Builder humidity(float humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder pressure(float pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder minTemperature(float minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        public Builder maxTemperature(float maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public Builder windDegree(float windDegree) {
            this.windDegree = windDegree;
            return this;
        }

        public Builder windSpeed(float windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder clouds(int clouds) {
            this.clouds = clouds;
            return this;
        }

        public Builder sunRiseTime(long sunRiseTime) {
            this.sunRiseTime = sunRiseTime;
            return this;
        }

        public Builder sunSetTime(long sunSetTime) {
            this.sunSetTime = sunSetTime;
            return this;
        }

        public Builder updateTime(long updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }
}
