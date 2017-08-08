package me.grechka.yamblz.yamblzweatherapp.models.response.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 22/07/2017.
 */

public final class CityResponse {

    @SerializedName("result")
    @Expose
    private CityInfo info;

    public CityInfo getInfo() {
        return info;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityResponse{");
        sb.append("info=").append(info);
        sb.append('}');
        return sb.toString();
    }
}
