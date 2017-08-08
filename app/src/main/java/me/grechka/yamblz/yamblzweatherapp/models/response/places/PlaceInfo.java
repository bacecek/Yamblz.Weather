package me.grechka.yamblz.yamblzweatherapp.models.response.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 22/07/2017.
 */

public final class PlaceInfo {

    @SerializedName("main_text")
    @Expose
    private String mainText;

    @SerializedName("secondary_text")
    @Expose
    private String secondaryText;

    public String getMainText() {
        return mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlaceInfo{");
        sb.append("mainText='").append(mainText).append('\'');
        sb.append(", secondaryText='").append(secondaryText).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
