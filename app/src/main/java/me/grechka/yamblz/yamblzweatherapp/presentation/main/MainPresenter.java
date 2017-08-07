package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
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

        updateCity();
    }

    void updateCity() {
        appRepository.getCity()
                .compose(schedulers.getIoToMainTransformerFlowable())
                .subscribe(getViewState()::setCityToHeader);
    }

    public void showWeather() {
        getViewState().showWeather();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
