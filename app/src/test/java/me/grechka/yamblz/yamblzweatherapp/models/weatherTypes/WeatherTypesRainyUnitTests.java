package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesRainyUnitTests {

    @Test
    public void WeatherTypes_rainyApplicable_whenWeatherIdIs500() {
        Weather weather = new Weather.Builder()
                .weatherId(500)
                .build();

        WeatherType rainy = new Rainy();
        assertTrue(rainy.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_rainyIsNotApplicable_whenWeatherIdIsNot500() {
        Weather weather = new Weather.Builder()
                .weatherId(805)
                .build();

        WeatherType rainy = new Rainy();
        assertFalse(rainy.isApplicable(weather));
    }
}
