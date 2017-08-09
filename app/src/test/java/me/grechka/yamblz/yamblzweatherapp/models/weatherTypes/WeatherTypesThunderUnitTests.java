package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesThunderUnitTests {

    @Test
    public void WeatherTypes_thunderIsApplicable_whenWeatherIdIs200() {
        Weather weather = new Weather.Builder()
                .weatherId(200)
                .build();

        WeatherType thunder = new Thunder();
        assertTrue(thunder.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_thunderIsApplicable_whenWeatherIdIs210() {
        Weather weather = new Weather.Builder()
                .weatherId(210)
                .build();

        WeatherType thunder = new Thunder();
        assertTrue(thunder.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_thunderIsNotApplicable_whenWeatherIdIsNot200() {
        Weather weather = new Weather.Builder()
                .weatherId(105)
                .build();

        WeatherType thunder = new Thunder();
        assertFalse(thunder.isApplicable(weather));
    }
}
