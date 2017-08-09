package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesSnowyUnitTests {

    @Test
    public void WeatherTypes_snowyIsApplicable_whenWeatherIdIs600() {
        Weather weather = new Weather.Builder()
                .weatherId(600)
                .build();

        WeatherType snowy = new Snowy();
        assertTrue(snowy.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_snowyIsApplicable_whenWeatherIdIs605() {
        Weather weather = new Weather.Builder()
                .weatherId(605)
                .build();

        WeatherType snowy = new Snowy();
        assertTrue(snowy.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_snowyIsNotApplicable_whenWeatherIdIsNot600() {
        Weather weather = new Weather.Builder()
                .weatherId(105)
                .build();

        WeatherType snowy = new Snowy();
        assertFalse(snowy.isApplicable(weather));
    }
}
