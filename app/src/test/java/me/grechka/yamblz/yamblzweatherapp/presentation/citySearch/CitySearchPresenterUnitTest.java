package me.grechka.yamblz.yamblzweatherapp.presentation.citySearch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.response.CityResponseModel;
import me.grechka.yamblz.yamblzweatherapp.repository.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.utils.JsonProvider;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

import static org.mockito.Mockito.*;

/**
 * Created by alexander on 30/07/2017.
 */

public class CitySearchPresenterUnitTest extends BaseUnitTest {

    private CitySearchPresenter presenter;
    private City sanJose  = new City.Builder()
            .placeId("ChIJ9T_5iuTKj4ARe3GfygqMnbk")
            .title("San Jose")
            .extendedTitle("San Jose, CA, United States")
            .build();

    @Mock CitySearchView view;
    @Mock
    AppRepository appRepository;
    @Mock RxSchedulers schedulers;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        presenter = new CitySearchPresenter(appRepository, schedulers);
        presenter.attachView(view);
    }

    @Override
    public void onMockInit() {
        CityResponseModel cityResponse = JsonProvider.openFile(CityResponseModel.class, "places-city.json");

        when(appRepository.obtainSuggestedCities(anyString()))
                .thenReturn(Observable.just(sanJose));

        when(appRepository.obtainCityInfo(anyString()))
                .thenReturn(Single.just(cityResponse));

        when(schedulers.getIoToMainTransformer())
                .thenReturn(observer -> observer);

        when(schedulers.getComputationToMainTransformerSingle())
                .thenReturn(observer -> observer);
    }

    @Test
    public void citySearchPresenter_attachView_theSuggestionListWasClearedAndLoadingHidden() {
        verify(view).hideLoading();
        verify(view).clearSuggestions();
    }

    @Test
    public void citySearchPresenter_fetchSuggestions_suggestionsWereObtainedAndDisplayed() {
        presenter.fetchSuggestions("san jose");

        verify(view, atLeast(1)).clearSuggestions();
        verify(view, atLeast(1)).hideLoading();
        verify(view).showLoading();
        verify(view).addSuggestion(any(City.class));
    }

    @Test
    public void citySearchPresenter_fetchCity_cityLocationWasFetchedAndCached() {
        presenter.fetchCity(sanJose);

        verify(view).closeDialog();
        verify(appRepository).saveCity(any(City.class));
    }
}
