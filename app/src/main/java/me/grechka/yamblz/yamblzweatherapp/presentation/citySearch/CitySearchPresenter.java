package me.grechka.yamblz.yamblzweatherapp.presentation.citySearch;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.data.NetworkRepository;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BasePresenter;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by alexander on 22/07/2017.
 */

@MainScope
@InjectViewState
public class CitySearchPresenter extends BasePresenter<CitySearchView> {

    private RxSchedulers schedulers;
    private NetworkRepository networkRepository;
    private DatabaseRepository databaseRepository;

    @Inject
    public CitySearchPresenter(@NonNull NetworkRepository networkRepository,
                               @NonNull DatabaseRepository databaseRepository,
                               @NonNull RxSchedulers schedulers) {
        this.schedulers = schedulers;
        this.networkRepository = networkRepository;
        this.databaseRepository = databaseRepository;
    }

    @Override
    public void attachView(CitySearchView view) {
        super.attachView(view);

        view.hideLoading();
        view.clearSuggestions();
    }

    public void setObservable(@NonNull Observable<CharSequence> observable) {
        addSubscription(observable.subscribe(this::fetchSuggestions));
    }

    public void fetchSuggestions(@NonNull CharSequence input) {
        getViewState().clearSuggestions();
        getViewState().showLoading();

        addSubscription(networkRepository.obtainSuggestedCities(input.toString())
                .compose(schedulers.getIoToMainTransformer())
                .subscribe(city -> {
                    getViewState().hideLoading();
                    getViewState().addSuggestion(city);
                }));
    }

    public void fetchCity(@NonNull City item) {
        addSubscription(networkRepository.obtainCityLocation(item.getPlaceId())
                .map(location -> new City.Builder(item)
                                .location(location)
                                .build())
                .flatMapCompletable(city -> databaseRepository.addCity(city))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::closeDialog));
    }
}
