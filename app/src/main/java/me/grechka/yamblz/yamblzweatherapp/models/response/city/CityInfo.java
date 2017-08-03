package me.grechka.yamblz.yamblzweatherapp.models.response.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 22/07/2017.
 */

public final class CityInfo {

    @SerializedName("geometry")
    @Expose
    private CityGeometry geometry;

    public CityGeometry getGeometry() {
        return geometry;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityInfo{");
        sb.append("geometry=").append(geometry);
        sb.append('}');
        return sb.toString();
    }
}
