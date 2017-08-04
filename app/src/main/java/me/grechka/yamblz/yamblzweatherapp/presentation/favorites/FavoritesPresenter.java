package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by alexander on 03/08/2017.
 */

@MainScope
@InjectViewState
public class FavoritesPresenter extends MvpPresenter<FavoritesView> {

    private RxSchedulers schedulers;
    private AppRepository repository;

    @Inject
    public FavoritesPresenter(@NonNull AppRepository repository,
                              @NonNull RxSchedulers schedulers) {
        this.repository = repository;
        this.schedulers = schedulers;

        repository.getCities()
                .compose(this.schedulers.getIoToMainTransformerFlowable())
                .subscribe(getViewState()::citiesListChanged);
    }

    public void selectCity(@NonNull City city) {
        repository.markCityAsActive(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCities);
    }

    public void removeCity(@NonNull City city) {
        repository.removeCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCities);
    }

    public void updateCities() {
    }
}
