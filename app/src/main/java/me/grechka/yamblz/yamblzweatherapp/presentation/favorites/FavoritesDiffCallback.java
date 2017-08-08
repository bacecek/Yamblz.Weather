package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

import me.grechka.yamblz.yamblzweatherapp.models.City;

/**
 * Created by alexander on 04/08/2017.
 */

public class FavoritesDiffCallback extends DiffUtil.Callback {

    private final List<City> oldList;
    private final List<City> newList;

    public FavoritesDiffCallback(@NonNull List<City> oldList,
                                 @NonNull List<City> newList) {
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
        return oldList.get(oldItemPosition)
                .getPlaceId()
                .equals(newList.get(newItemPosition).getPlaceId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
