package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesFoggyUnitTests {

    @Test
    public void WeatherTypes_foggyIsApplicable_whenWeahterIdIs700() {
        Weather weather = new Weather.Builder()
                .weatherId(700)
                .build();

        WeatherType foggy = new Foggy();
        assertTrue(foggy.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_foggyIsApplicable_whenWeahterIdIs703() {
        Weather weather = new Weather.Builder()
                .weatherId(703)
                .build();

        WeatherType foggy = new Foggy();
        assertTrue(foggy.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_foggyIsNotApplicable_whenWeatherIdIsNot700() {
        Weather weather = new Weather.Builder()
                .weatherId(305)
                .build();

        WeatherType foggy = new Foggy();
        assertFalse(foggy.isApplicable(weather));
    }
}
