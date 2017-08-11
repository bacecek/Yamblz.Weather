package me.grechka.yamblz.yamblzweatherapp.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherBuilderTest {

    @Test
    public void WeatherBuilder_createWeatherWithBuilder_success() {
        Weather weather = new Weather.Builder()
                .weatherId(800)
                .humidity(100.0f)
                .pressure(12.0f)
                .temperature(100.0f)
                .minTemperature(100.0f)
                .maxTemperature(100.0f)
                .windDegree(5.0f)
                .windSpeed(1.0f)
                .clouds(1)
                .updateTime(1000L)
                .sunRiseTime(1000L)
                .sunSetTime(1000L)
                .build();

        assertEquals(weather.getWeatherId(), 800);
        assertEquals(weather.getHumidity(), 100.0f);
        assertEquals(weather.getPressure(), 12.0f);
        assertEquals(weather.getTemperature(), 100.0f);
        assertEquals(weather.getMinTemperature(), 100.0f);
        assertEquals(weather.getMaxTemperature(), 100.0f);
        assertEquals(weather.getWindDegree(), 5.0f);
        assertEquals(weather.getWindSpeed(), 1.0f);
        assertEquals(weather.getClouds(), 1);
        assertEquals(weather.getUpdateTime(), 1000L);
        assertEquals(weather.getSunRiseTime(), 1000L);
        assertEquals(weather.getSunSetTime(), 1000L);
    }

    @Test
    public void WeatherBuilder_copyWeatherAndEditWithBuilder_success() {
        Weather laWeather = new Weather.Builder()
                .weatherId(800)
                .humidity(100.0f)
                .pressure(12.0f)
                .temperature(100.0f)
                .minTemperature(100.0f)
                .maxTemperature(100.0f)
                .windDegree(5.0f)
                .windSpeed(1.0f)
                .clouds(1)
                .updateTime(1000L)
                .sunRiseTime(1000L)
                .sunSetTime(1000L)
                .build();

        Weather weather = new Weather.Builder(laWeather)
                .clouds(2)
                .weatherId(200)
                .build();

        assertEquals(weather.getWeatherId(), 200);
        assertEquals(weather.getHumidity(), 100.0f);
        assertEquals(weather.getPressure(), 12.0f);
        assertEquals(weather.getTemperature(), 100.0f);
        assertEquals(weather.getMinTemperature(), 100.0f);
        assertEquals(weather.getMaxTemperature(), 100.0f);
        assertEquals(weather.getWindDegree(), 5.0f);
        assertEquals(weather.getWindSpeed(), 1.0f);
        assertEquals(weather.getClouds(), 2);
        assertEquals(weather.getUpdateTime(), 1000L);
        assertEquals(weather.getSunRiseTime(), 1000L);
        assertEquals(weather.getSunSetTime(), 1000L);
    }
}
