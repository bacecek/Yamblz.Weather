
package me.grechka.yamblz.yamblzweatherapp.models.response.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp {

    @SerializedName("day")
    @Expose
    private float day;
    
    @SerializedName("min")
    @Expose
    private float min;
    
    @SerializedName("max")
    @Expose
    private float max;
    
    @SerializedName("night")
    @Expose
    private float night;
    
    @SerializedName("eve")
    @Expose
    private float eve;
    
    @SerializedName("morn")
    @Expose
    private float morn;

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getEve() {
        return eve;
    }

    public void setEve(float eve) {
        this.eve = eve;
    }

    public float getMorn() {
        return morn;
    }

    public void setMorn(float morn) {
        this.morn = morn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Temp{");
        sb.append("day=").append(day);
        sb.append(", min=").append(min);
        sb.append(", max=").append(max);
        sb.append(", night=").append(night);
        sb.append(", eve=").append(eve);
        sb.append(", morn=").append(morn);
        sb.append('}');
        return sb.toString();
    }
}
