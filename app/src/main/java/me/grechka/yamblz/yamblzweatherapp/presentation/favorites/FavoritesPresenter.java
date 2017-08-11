package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BasePresenter;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by alexander on 03/08/2017.
 */

@MainScope
@InjectViewState
public class FavoritesPresenter extends BasePresenter<FavoritesView> {

    private static final String TAG = FavoritesPresenter.class.getCanonicalName();

    private RxSchedulers schedulers;
    private DatabaseRepository repository;

    @Inject
    public FavoritesPresenter(@NonNull DatabaseRepository repository,
                              @NonNull RxSchedulers schedulers) {
        this.repository = repository;
        this.schedulers = schedulers;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        addSubscription(repository.getCities()
                .compose(this.schedulers.getIoToMainTransformerFlowable())
                .subscribe(this::updateCities));
    }

    public void selectCity(@NonNull City city) {
        addSubscription(repository.markCityAsActive(city)
                .subscribeOn(Schedulers.io())
                .subscribe());
    }

    public void removeCity(@NonNull City city) {
        addSubscription(repository.removeCity(city)
                .subscribeOn(Schedulers.io())
                .subscribe());
    }

    public void updateCities(@NonNull List<City> cities) {
        Log.d(TAG, cities.toString());

        if (cities.isEmpty()) {
            getViewState().onEmptyList();
            return;
        }

        if (!cities.get(0).isActive()) {
            getViewState().onActiveMissing(cities);
            return;
        }

        getViewState().onListChanged(cities);
    }
}
