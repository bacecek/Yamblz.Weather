package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.repository.AppRepository;

/**
 * Created by Grechka on 14.07.2017.
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private AppRepository appRepository;

    @Inject
    public MainPresenter(@NonNull AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        updateCity();
    }

    void updateCity() {
        getViewState().setCityToHeader(appRepository.getCity());
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
