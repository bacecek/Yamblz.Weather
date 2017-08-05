package me.grechka.yamblz.yamblzweatherapp.presentation.forecast;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by alexander on 04/08/2017.
 */

public class ForecastDiffCallback extends DiffUtil.Callback {

    private final List<Weather> oldList;
    private final List<Weather> newList;

    public ForecastDiffCallback(@NonNull List<Weather> oldList,
                                @NonNull List<Weather> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
