package me.grechka.yamblz.yamblzweatherapp;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.domain.Interactor;
import me.grechka.yamblz.yamblzweatherapp.domain.InteractorImp;
import me.grechka.yamblz.yamblzweatherapp.models.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.models.response.CurrentWeatherResponse;
import me.grechka.yamblz.yamblzweatherapp.utils.JsonProvider;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InteractorUnitTest extends BaseUnitTest {

    @Mock Context context;
    @Mock Resources resources;

    private Interactor interactor;
    private CurrentWeather weather;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        interactor = new InteractorImp(context);

        weather = new CurrentWeather(
                "27", "clear sky", "51",
                "26", "28", "5 м/с, SW"
        );
    }

    @Override
    public void onMockInit() {
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(anyInt())).thenReturn("SW");
    }

    @Test
    public void interactor_parsedCorrectly_whenResponseWithGoodData() {
        CurrentWeatherResponse weatherResponse = JsonProvider
                .openFile(CurrentWeatherResponse.class, "openweather-weather.json");
        CurrentWeather weather = interactor.getCurrentWeatherFromResponse(weatherResponse);
        assertEquals(this.weather, weather);
    }

    @Test(expected = NumberFormatException.class)
    public void interactor_thrownAnError_whenResponseWithBrokenCoordinates() throws IOException {
        CurrentWeatherResponse weatherResponse = JsonProvider
                .openFile(CurrentWeatherResponse.class, "openweather-weather-brokencoordinates.json");
        interactor.getCurrentWeatherFromResponse(weatherResponse);
    }

    @Test(expected = NullPointerException.class)
    public void interactor_thrownAnError_whenResponseWithoutBaseFields() throws IOException {
        CurrentWeatherResponse weatherResponse = JsonProvider
                .openFile(CurrentWeatherResponse.class, "openweather-weather-withoutbasefields.json");
        interactor.getCurrentWeatherFromResponse(weatherResponse);
    }
}