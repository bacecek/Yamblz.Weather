package me.grechka.yamblz.yamblzweatherapp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexander on 22/07/2017.
 */

public final class SuggestionResponseModel {

    @SerializedName("predictions")
    @Expose
    private List<Place> predictions;

    public List<Place> getPredictions() {
        return predictions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SuggestionResponseModel{");
        sb.append("predictions=").append(predictions);
        sb.append('}');
        return sb.toString();
    }
}
