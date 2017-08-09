package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;

import java.util.Date;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesClearSkyUnitTests {

    @Test
    public void WeatherTypes_clearSkyIsApplicable_whenWeatherIdIs800AndTimeIsRight() {
        long currentTime = new Date().getTime();

        Weather weather = new Weather.Builder()
                .weatherId(800)
                .sunRiseTime(currentTime - 1000)
                .sunSetTime(currentTime - 9000)
                .build();

        WeatherType clearSky = new ClearSky();
        assertTrue(clearSky.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_clearSkyNotApplicable_whenWeatherIdIs800AndTimeIsNotRight() {
        long currentTime = new Date().getTime();

        Weather weather = new Weather.Builder()
                .weatherId(800)
                .sunRiseTime(currentTime - 11000)
                .sunSetTime(currentTime + 11000)
                .build();

        WeatherType clearSky = new ClearSky();
        assertFalse(clearSky.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_clearSkyNotApplicable_whenWeatherIdIs805() {
        Weather weather = new Weather.Builder()
                .weatherId(805)
                .build();

        WeatherType clearSky = new ClearSky();
        assertFalse(clearSky.isApplicable(weather));
    }
}
