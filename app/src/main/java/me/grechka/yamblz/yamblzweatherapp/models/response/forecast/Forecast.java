package me.grechka.yamblz.yamblzweatherapp.models.response.forecast;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.grechka.yamblz.yamblzweatherapp.models.response.weather.Weather;

public class Forecast {

    @SerializedName("dt")
    @Expose
    private int dt;

    @SerializedName("temp")
    @Expose
    private Temp temp;

    @SerializedName("pressure")
    @Expose
    private double pressure;

    @SerializedName("humidity")
    @Expose
    private int humidity;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather;

    @SerializedName("speed")
    @Expose
    private double speed;

    @SerializedName("deg")
    @Expose
    private int deg;

    @SerializedName("clouds")
    @Expose
    private int clouds;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Forecast{");
        sb.append("dt=").append(dt);
        sb.append(", temp=").append(temp);
        sb.append(", pressure=").append(pressure);
        sb.append(", humidity=").append(humidity);
        sb.append(", weather=").append(weather);
        sb.append(", speed=").append(speed);
        sb.append(", deg=").append(deg);
        sb.append(", clouds=").append(clouds);
        sb.append('}');
        return sb.toString();
    }
}
