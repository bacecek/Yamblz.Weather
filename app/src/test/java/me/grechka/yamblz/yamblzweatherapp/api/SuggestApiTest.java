package me.grechka.yamblz.yamblzweatherapp.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URLEncoder;

import io.reactivex.observers.TestObserver;
import me.grechka.yamblz.yamblzweatherapp.base.BaseApiTest;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityResponse;
import me.grechka.yamblz.yamblzweatherapp.models.response.places.Place;
import me.grechka.yamblz.yamblzweatherapp.models.response.places.SuggestionResponse;
import me.grechka.yamblz.yamblzweatherapp.data.net.SuggestApi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by alexander on 27/07/2017.
 */

@RunWith(JUnit4.class)
public class SuggestApiTest extends BaseApiTest {

    private static final double EPS = 0.00000001;

    private SuggestApi api;

    @Before
    public void createService() throws IOException {
        api = createService(SuggestApi.class);
    }

    @After
    public void destroyService() throws IOException {
        stopService();
    }

    @Test
    public void suggestApi_obtainPlacesSuggestion_parsedCorrectlyWithGsonExposeAnnotationEnabled()
            throws IOException, InterruptedException {
        enqueueResponse("places-suggestion.json");

        String input = "San-Jose";
        String type = SuggestApi.API_TYPES;
        String apiKey = "123456";

        TestObserver<SuggestionResponse> observer = api.obtainSuggestedCities(input, type, apiKey).test();

        observer
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(check((SuggestionResponse s) ->
                    assertThat(s.getPredictions().size(), is(2))))
                .assertValue(check((SuggestionResponse s) -> {
                    Place sanJoseUs = s.getPredictions().get(0);

                    assertEquals("ChIJ9T_5iuTKj4ARe3GfygqMnbk", sanJoseUs.getPlaceId());
                    assertEquals("San Jose, CA, United States", sanJoseUs.getDescription());
                    assertEquals("San Jose", sanJoseUs.getPlaceInfo().getMainText());

                    Place sanJoseCr = s.getPredictions().get(1);

                    assertEquals("ChIJxRUNxULjoI8RgrgRn2pqdOY", sanJoseCr.getPlaceId());
                    assertEquals("San Jose, San José Province, Costa Rica", sanJoseCr.getDescription());
                    assertEquals("San Jose", sanJoseCr.getPlaceInfo().getMainText());
                }));

        String urlEncodedInput = URLEncoder.encode(input, "UTF-8");
        String url = String.format("/autocomplete/json?input=%1$s&types=%2$s&key=%3$s",
                urlEncodedInput, type, apiKey);
        assertEquals(getRequest().getPath(), url);
    }

    @Test
    public void SuggestApi_cityParsedCorrectly_whenWithGsonExposeAnnotationEnabled()
            throws IOException, InterruptedException {
        enqueueResponse("places-city.json");

        String placeId = "ChIJxRUNxULjoI8RgrgRn2pqdOY";
        String apiKey = "123456";

        double expectedLatitude = 9.9280694;
        double expectedLongitude = -84.0907246;

        TestObserver<CityResponse> observer = api.obtainCity(placeId, apiKey).test();

        observer
                .assertNoErrors()
                .assertValue((CityResponse city) -> {
                    CityLocation location = city.getInfo().getGeometry().getLocation();
                    return Math.abs(location.getLatitude() - expectedLatitude) < EPS &&
                    Math.abs(location.getLongitude() - expectedLongitude) < EPS;
                });

        String url = String.format("/details/json?placeid=%1$s&key=%2$s", placeId, apiKey);
        assertEquals(getRequest().getPath(), url);
    }
}
