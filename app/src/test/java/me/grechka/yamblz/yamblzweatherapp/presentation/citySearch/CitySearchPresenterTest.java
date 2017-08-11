package me.grechka.yamblz.yamblzweatherapp.presentation.citySearch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.data.NetworkRepository;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 11/08/2017.
 */
public class CitySearchPresenterTest extends BaseTest {

    private CitySearchPresenter citySearchPresenter;

    @Mock CitySearchView view;

    @Mock RxSchedulers schedulers;
    @Mock NetworkRepository networkRepository;
    @Mock DatabaseRepository databaseRepository;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        citySearchPresenter = new CitySearchPresenter(networkRepository, databaseRepository, schedulers);
        citySearchPresenter.attachView(view);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();

        City city = new City.Builder().build();

        when(networkRepository.obtainSuggestedCities(anyString())).thenReturn(Observable.just(city));
        when(schedulers.getIoToMainTransformer()).thenReturn(observer -> observer);
        when(networkRepository.obtainCityLocation(anyString())).thenReturn(Single.just(new CityLocation()));
    }

    @Test
    public void attachSuccess_whenHideLoadingAndClearSuggestionCalls() {
        CitySearchPresenter presenter = new CitySearchPresenter(networkRepository, databaseRepository, schedulers);
        CitySearchView v = Mockito.mock(CitySearchView.class);
        presenter.attachView(v);

        verify(v).hideLoading();
        verify(v).clearSuggestions();
    }

    @Test
    public void setObservable() {
        citySearchPresenter.setObservable(Observable.just("test"));

        verify(view, atLeastOnce()).clearSuggestions();
        verify(view, atLeastOnce()).showLoading();
        verify(networkRepository).obtainSuggestedCities(anyString());
        verify(view, atLeastOnce()).hideLoading();
        verify(view, atLeastOnce()).addSuggestion(any(City.class));
    }

    @Test
    public void fetchSuggestions() {
        citySearchPresenter.fetchSuggestions("test");

        verify(networkRepository).obtainSuggestedCities(anyString());
        verify(view, atLeastOnce()).hideLoading();
        verify(view, atLeastOnce()).addSuggestion(any(City.class));
    }

    @Test
    public void fetchCity() {
        citySearchPresenter.fetchCity(new City.Builder().build());

        verify(networkRepository).obtainCityLocation(anyString());
        //verify(databaseRepository).addCity(any(City.class));
    }
}