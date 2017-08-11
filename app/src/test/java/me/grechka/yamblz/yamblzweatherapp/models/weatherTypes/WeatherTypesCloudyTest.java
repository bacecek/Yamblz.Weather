package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesCloudyTest {

    @Test
    public void WeatherTypes_cloudyIsNotApplicable_whenWeatherIdIs800() {
        Weather weather = new Weather.Builder()
                .weatherId(800)
                .build();

        WeatherType cloudy = new Cloudy();
        assertFalse(cloudy.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_cloudyIsApplicable_whenWeatherIdIs805() {
        Weather weather = new Weather.Builder()
                .weatherId(805)
                .build();

        WeatherType cloudy = new Cloudy();
        assertTrue(cloudy.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_cloudyIsNotApplicable_whenWeatherIdIsNot600() {
        Weather weather = new Weather.Builder()
                .weatherId(105)
                .build();

        WeatherType cloudy = new Cloudy();
        assertFalse(cloudy.isApplicable(weather));
    }
}
