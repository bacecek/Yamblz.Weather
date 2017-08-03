package me.grechka.yamblz.yamblzweatherapp.models.response.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 22/07/2017.
 */

public final class Place {

    @SerializedName("place_id")
    @Expose
    private String placeId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("structured_formatting")
    @Expose
    private PlaceInfo placeInfo;

    public String getPlaceId() {
        return placeId;
    }

    public String getDescription() {
        return description;
    }

    public PlaceInfo getPlaceInfo() {
        return placeInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Place{");
        sb.append("placeId='").append(placeId).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", placeInfo=").append(placeInfo);
        sb.append('}');
        return sb.toString();
    }
}
