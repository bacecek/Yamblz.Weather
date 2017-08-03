package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

/**
 * Created by Grechka on 14.07.2017.
 */

@MainScope
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private RxSchedulers schedulers;
    private AppRepository appRepository;

    @Inject
    public MainPresenter(@NonNull RxSchedulers schedulers,
                         @NonNull AppRepository appRepository) {
        this.schedulers = schedulers;
        this.appRepository = appRepository;
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        updateCity();
    }

    void updateCity() {
        appRepository.getCity()
                .compose(schedulers.getIoToMainTransformerSingle())
                .subscribe(getViewState()::setCityToHeader, Throwable::printStackTrace);
    }

    public void showWeather() {
        getViewState().showWeather();
    }

    public void showSettings() {
        getViewState().showSettings();
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
