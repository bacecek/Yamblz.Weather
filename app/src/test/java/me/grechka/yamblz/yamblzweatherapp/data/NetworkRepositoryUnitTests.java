package me.grechka.yamblz.yamblzweatherapp.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;
import me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi;
import me.grechka.yamblz.yamblzweatherapp.helpers.JsonProvider;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityResponse;
import me.grechka.yamblz.yamblzweatherapp.models.response.places.SuggestionResponse;
import me.grechka.yamblz.yamblzweatherapp.models.response.weather.WeatherResponse;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 09/08/2017.
 */

public class NetworkRepositoryUnitTests extends BaseUnitTest {

    private NetworkRepository repository;

    @Mock WeatherApi weatherApi;
    @Mock SuggestApi suggestApi;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        repository = new NetworkRepositoryImpl(weatherApi, suggestApi);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();

        WeatherResponse weatherResponse = JsonProvider.openFile(WeatherResponse.class, "openweather-weather.json");
        when(weatherApi.getWeatherByLocation(anyDouble(), anyDouble(), anyString())).thenReturn(Single.just(weatherResponse));

        CityResponse cityResponse = JsonProvider.openFile(CityResponse.class, "places-city.json");
        when(suggestApi.obtainCity(anyString(), anyString())).thenReturn(Single.just(cityResponse));

        SuggestionResponse suggestionResponse = JsonProvider.openFile(SuggestionResponse.class, "places-suggestion.json");
        when(suggestApi.obtainSuggestedCities(anyString(), anyString(),
                anyString())).thenReturn(Single.just(suggestionResponse));
    }

    @Test
    public void NetworkRepository_getWeatherByLocation_whenResponseIsCorrect() {
        Weather expected = new Weather.Builder()
                .weatherId(800)
                .temperature(26.76f)
                .minTemperature(26.0f)
                .maxTemperature(28.0f)
                .humidity(51.0f)
                .pressure(1007)
                .windSpeed(5.0f)
                .sunRiseTime(1501118808000L)
                .sunSetTime(1501177443000L)
                .build();

        TestObserver<Weather> observer = repository.getWeatherByLocation(0, 0).test();

        observer
                .assertValueCount(1)
                .assertValue(expected);
    }

    @Test
    public void NetworkRepository_getForecastByLocation() {
        repository.getForecastByLocation(0, 0, "123456");
        verify(weatherApi).getForecastByLocation(anyDouble(), anyDouble(),
                anyInt(), anyString());

    }

    @Test
    public void NetowrkRepository_obtainCityLocation() {
        CityLocation expectedLocation = new CityLocation(9.9280694, -84.0907246);

        TestObserver<CityLocation> observer = repository.obtainCityLocation("12").test();

        observer
                .assertValueCount(1)
                .assertValue(expectedLocation);
    }

    @Test
    public void NetworkRepository_obtainSuggestedCities() {
        City sanJoseUs = new City.Builder()
                .title("San Jose")
                .extendedTitle("San Jose, CA, United States")
                .placeId("ChIJ9T_5iuTKj4ARe3GfygqMnbk")
                .build();

        City sanJoseCr = new City.Builder()
                .title("San Jose")
                .extendedTitle("San Jose, San José Province, Costa Rica")
                .placeId("ChIJxRUNxULjoI8RgrgRn2pqdOY")
                .build();

        TestObserver<City> observer = repository.obtainSuggestedCities("").test();

        observer
                .assertValueCount(2)
                .assertValues(sanJoseUs, sanJoseCr);
    }
}
