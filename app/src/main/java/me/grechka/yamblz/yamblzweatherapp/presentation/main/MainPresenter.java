package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by Grechka on 14.07.2017.
 */

@MainScope
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private RxSchedulers schedulers;
    private DatabaseRepository appRepository;

    @Inject
    public MainPresenter(@NonNull RxSchedulers schedulers,
                         @NonNull DatabaseRepository appRepository) {
        this.schedulers = schedulers;
        this.appRepository = appRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updateCity();
    }

    void updateCity() {
        appRepository.getCity()
                .compose(schedulers.getIoToMainTransformerFlowable())
                .subscribe(this::onCityChanged);
    }

    public void onCityChanged(@NonNull City city) {
        getViewState().setCityToHeader(city);
    }

    public void showMissedCity() {
        getViewState().onCityMissedError();
    }

    public void showSettings() {
        getViewState().showSettings();
    }

    public void showFavorites() {
        getViewState().showFavorites();
    }

    public void showAbout() {
        getViewState().showAbout();
    }

    public void goBack() {
        getViewState().goBack();
    }

    public void navigate(int screenId) {
        getViewState().navigate(screenId);
    }
}
