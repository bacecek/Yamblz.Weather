package me.grechka.yamblz.yamblzweatherapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.domain.WeatherInteractor;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.response.places.SuggestionResponse;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepositoryImpl;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;
import me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi;
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
    @Mock
    WeatherInteractor weatherInteractor;

    private AppRepository appRepository;
    private City[] cities = new City[2];

    @Before
    @Override
    public void onInit() {
        super.onInit();

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
    }

    @Test
    public void repository_obtainSuggestionList_expectedCorrectPlaceModel() {
        SuggestionResponse suggestions =
                JsonProvider.openFile(SuggestionResponse.class, "places-suggestion.json");

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
        SuggestionResponse suggestions =
                JsonProvider.openFile(SuggestionResponse.class, "places-suggestion-single-broken.json");

        when(suggestApi.obtainSuggestedCities(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(suggestions));

        TestObserver<City> observer = appRepository.obtainSuggestedCities("San Jose").test();
        observer
                .assertTerminated()
                .assertError(IllegalArgumentException.class);
    }
}
