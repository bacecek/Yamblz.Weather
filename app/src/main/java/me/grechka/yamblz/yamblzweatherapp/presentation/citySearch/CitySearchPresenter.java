package me.grechka.yamblz.yamblzweatherapp.presentation.citySearch;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by alexander on 22/07/2017.
 */

@MainScope
@InjectViewState
public class CitySearchPresenter extends MvpPresenter<CitySearchView> {

    private RxSchedulers schedulers;
    private AppRepository appAppRepository;

    @Inject
    public CitySearchPresenter(@NonNull AppRepository appAppRepository,
                               @NonNull RxSchedulers schedulers) {
        this.schedulers = schedulers;
        this.appAppRepository = appAppRepository;
    }

    @Override
    public void attachView(CitySearchView view) {
        super.attachView(view);
        view.hideLoading();
        view.clearSuggestions();
    }

    public void setObservable(@NonNull Observable<CharSequence> observable) {
        observable.subscribe(this::fetchSuggestions);
    }

    public void fetchSuggestions(@NonNull CharSequence input) {
        getViewState().clearSuggestions();
        getViewState().showLoading();

        this.appAppRepository.obtainSuggestedCities(input.toString())
                .compose(schedulers.getIoToMainTransformer())
                .subscribe(city -> {
                    getViewState().hideLoading();
                    getViewState().addSuggestion(city);
                });
    }

    public void fetchCity(@NonNull City item) {
        appAppRepository.obtainCityLocation(item.getPlaceId())
                .compose(schedulers.getComputationToMainTransformerSingle())
                .map(location -> new City.Builder(item)
                                .location(location)
                                .build())
                .flatMapCompletable(city -> appAppRepository.addCity(city))
                .subscribe(getViewState()::closeDialog);
    }
}
