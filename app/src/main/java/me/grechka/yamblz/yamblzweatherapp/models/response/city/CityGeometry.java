package me.grechka.yamblz.yamblzweatherapp.models.response.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 22/07/2017.
 */

public class CityGeometry {

    @SerializedName("location")
    @Expose
    private CityLocation location;

    public CityLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityGeometry{");
        sb.append("location=").append(location);
        sb.append('}');
        return sb.toString();
    }
}
