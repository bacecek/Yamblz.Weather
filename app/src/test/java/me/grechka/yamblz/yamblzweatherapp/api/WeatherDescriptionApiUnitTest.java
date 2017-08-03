package me.grechka.yamblz.yamblzweatherapp.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.matchers.GreaterThan;

import java.io.IOException;
import java.util.Locale;

import io.reactivex.observers.TestObserver;
import me.grechka.yamblz.yamblzweatherapp.base.BaseApiUnitTest;
import me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by alexander on 27/07/2017.
 */

@RunWith(JUnit4.class)
public class WeatherDescriptionApiUnitTest extends BaseApiUnitTest {

    private static final double EPS = 0.01;

    private WeatherApi api;

    @Before
    public void createService() throws IOException {
        api = createService(WeatherApi.class);
    }

    @After
    public void destroyService() throws IOException {
       stopService();
    }

    @Test
    public void weatherApi_obtainWeatherForCoordinates_parsedCorrectlyWithGsonExposeAnnotationEnabled()
            throws IOException, InterruptedException {
        enqueueResponse("openweather-weather.json");

        double latitude = 55.75;
        double longitude = 37.62;
        String units = "metric";
        String apiKey = "123456";

        double expectedTemperature = 26.76;
        double expectedMaxTemperature = 28.0;
        double expectedMinTemperature = 26.0;
        double expectedSpeed = 5.0;
        int expectedHumidity = 51;
        String expectedDescription = "clear sky";

        TestObserver<WeatherResponse> observer =
                api.getWeatherByLocation(latitude, longitude, units, apiKey).test();

        observer
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(check(weather -> {
                    assertThat(weather.getWeatherDescription().size(), new GreaterThan<>(0));
                    WeatherDescription w = weather.getWeatherDescription().get(0);

                    assertEquals(expectedTemperature, weather.getWeatherInfo().getTemp(), EPS);
                    assertEquals(expectedDescription, w.getDescription());
                    assertEquals(expectedHumidity, weather.getWeatherInfo().getHumidity());
                    assertEquals(expectedMaxTemperature, weather.getWeatherInfo().getTempMax(), EPS);
                    assertEquals(expectedMinTemperature, weather.getWeatherInfo().getTempMin(), EPS);
                    assertEquals(expectedSpeed, weather.getWind().getSpeed(), EPS);
                }));

        String url = String.format(Locale.US,
                "/data/2.5/weather?lat=%1$.2f&lon=%2$.2f&units=metric&appid=%3$s", latitude, longitude, apiKey);
        assertEquals(getRequest().getPath(), url);
    }
}
