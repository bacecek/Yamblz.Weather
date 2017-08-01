package me.grechka.yamblz.yamblzweatherapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.interactor.Interactor;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.response.SuggestionResponseModel;
import me.grechka.yamblz.yamblzweatherapp.repository.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.repository.AppRepositoryImp;
import me.grechka.yamblz.yamblzweatherapp.repository.net.SuggestApi;
import me.grechka.yamblz.yamblzweatherapp.repository.net.WeatherApi;
import me.grechka.yamblz.yamblzweatherapp.repository.prefs.PreferencesManager;
import me.grechka.yamblz.yamblzweatherapp.utils.JsonProvider;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 27/07/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class AppRepositoryUnitTest extends BaseUnitTest {

    @Mock SuggestApi suggestApi;
    @Mock WeatherApi weatherApi;
    @Mock Interactor interactor;
    @Mock PreferencesManager prefs;

    private AppRepository appRepository;
    private City[] cities = new City[2];

    @Before
    @Override
    public void onInit() {
        super.onInit();
        appRepository = new AppRepositoryImp(interactor, weatherApi, suggestApi, prefs);

        cities[0] = new City.Builder()
                        .placeId("ChIJ9T_5iuTKj4ARe3GfygqMnbk")
                        .title("San Jose")
                        .extendedTitle("San Jose, CA, United States")
                        .build();

        cities[1] = new City.Builder()
                .placeId("ChIJxRUNxULjoI8RgrgRn2pqdOY")
                .title("San Jose")
                .extendedTitle("San Jose, San José Province, Costa Rica")
                .build();
    }

    @Override
    public void onMockInit() {
        when(prefs.getCurrentCity()).thenReturn(null);
    }

    @Test
    public void repository_obtainSuggestionList_expectedCorrectPlaceModel() {
        SuggestionResponseModel suggestions =
                JsonProvider.openFile(SuggestionResponseModel.class, "places-suggestion.json");

        when(suggestApi.obtainSuggestedCities(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(suggestions));

        TestObserver<City> observer = appRepository.obtainSuggestedCities("San Jose").test();
        observer
                .assertNoErrors()
                .assertValueCount(2)
                .assertValues(cities);
    }

    @Test
    public void repository_obtainBrokenSuggestionList_expectedThrowAnException() {
        SuggestionResponseModel suggestions =
                JsonProvider.openFile(SuggestionResponseModel.class, "places-suggestion-single-broken.json");

        when(suggestApi.obtainSuggestedCities(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(suggestions));

        TestObserver<City> observer = appRepository.obtainSuggestedCities("San Jose").test();
        observer
                .assertTerminated()
                .assertError(IllegalArgumentException.class);
    }
}
