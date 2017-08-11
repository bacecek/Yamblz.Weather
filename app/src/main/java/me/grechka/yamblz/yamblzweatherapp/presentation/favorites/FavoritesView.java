package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import me.grechka.yamblz.yamblzweatherapp.models.City;

/**
 * Created by alexander on 03/08/2017.
 */

public interface FavoritesView extends MvpView {
    void onListChanged(@NonNull List<City> cities);
    void onActiveMissing(@NonNull List<City> cities);
    void onEmptyList();
}
