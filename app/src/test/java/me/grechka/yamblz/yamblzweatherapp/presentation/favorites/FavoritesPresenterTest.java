package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 11/08/2017.
 */
public class FavoritesPresenterTest extends BaseTest {

    private FavoritesPresenter favoritesPresenter;

    @Mock FavoritesView view;

    @Mock RxSchedulers schedulers;
    @Mock DatabaseRepository repository;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        favoritesPresenter = new FavoritesPresenter(repository, schedulers);
        favoritesPresenter.attachView(view);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();
        when(repository.getCities()).thenReturn(Flowable.just(new ArrayList<>()));
        when(schedulers.getIoToMainTransformerFlowable()).thenReturn(f -> f);
    }

    @Test
    public void firstViewAttach_withEmptyList() {
        FavoritesPresenter presenter = new FavoritesPresenter(repository, schedulers);
        FavoritesView v = Mockito.mock(FavoritesView.class);
        when(repository.getCities()).thenReturn(Flowable.just(new ArrayList<>()));

        presenter.attachView(v);
        verify(v).onEmptyList();
    }

    @Test
    public void firstViewAttach_withInActiveList() {
        FavoritesPresenter presenter = new FavoritesPresenter(repository, schedulers);
        FavoritesView v = Mockito.mock(FavoritesView.class);
        List<City> cities = new ArrayList<>();
        cities.add(new City.Builder().build());

        when(repository.getCities()).thenReturn(Flowable.just(cities));
        when(repository.markCityAsActive(any(City.class))).thenReturn(Completable.complete());
        when(repository.removeCity(any(City.class))).thenReturn(Completable.complete());

        presenter.attachView(v);
        verify(v).onActiveMissing(anyList());
    }

    @Test
    public void firstViewAttach_withList() {
        FavoritesPresenter presenter = new FavoritesPresenter(repository, schedulers);
        FavoritesView v = Mockito.mock(FavoritesView.class);
        List<City> cities = new ArrayList<>();
        cities.add(new City.Builder().isActive(true).build());

        when(repository.getCities()).thenReturn(Flowable.just(cities));

        presenter.attachView(v);
        verify(v).onListChanged(anyList());
    }
}