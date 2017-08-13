package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.reactivex.Flowable;
import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 11/08/2017.
 */

public class MainPresenterTest extends BaseTest {

    private MainPresenter presenter;

    @Mock MainView view;

    @Mock RxSchedulers schedulers;
    @Mock DatabaseRepository repository;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        presenter = new MainPresenter(schedulers, repository);
        presenter.attachView(view);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();
        City city = new City.Builder().build();

        when(repository.getCity()).thenReturn(Flowable.just(city));
        when(schedulers.getIoToMainTransformerFlowable()).thenReturn(f -> f);
    }

    @Test
    public void onFirstAttach() {
        MainPresenter mainPresenter = new MainPresenter(schedulers, repository);
        MainView mainView = Mockito.mock(MainView.class);

        mainPresenter.attachView(mainView);
        verify(mainView).setCityToHeader(any(City.class));
    }

    @Test
    public void showMissedCity() {
        presenter.showMissedCity();
        verify(view).onCityMissedError();
    }

    @Test
    public void showSettings() {
        presenter.showSettings();
        verify(view).showSettings();
    }

    @Test
    public void showFavorites() {
        presenter.showFavorites();
        verify(view).showFavorites();
    }

    @Test
    public void showAbout() {
        presenter.showAbout();
        verify(view).showAbout();
    }

    @Test
    public void goBack() {
        presenter.goBack();
        verify(view).goBack();
    }

    @Test
    public void navigate() {
        presenter.navigate(0);
        verify(view).navigate(anyInt());
    }
}