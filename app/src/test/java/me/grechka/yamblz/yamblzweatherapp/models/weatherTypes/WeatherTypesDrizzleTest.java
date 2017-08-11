package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesDrizzleTest {

    @Test
    public void WeatherTypes_drizzleIsApplicable_whenWeatherIdIs300() {
        Weather weather = new Weather.Builder()
                .weatherId(300)
                .build();

        WeatherType drizzle = new Drizzle();
        assertTrue(drizzle.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_drizzleIsApplicable_whenWeatherIdIs303() {
        Weather weather = new Weather.Builder()
                .weatherId(303)
                .build();

        WeatherType drizzle = new Drizzle();
        assertTrue(drizzle.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_drizzleIsNotApplicable_whenWeatherIdIsNot300() {
        Weather weather = new Weather.Builder()
                .weatherId(105)
                .build();

        WeatherType drizzle = new Drizzle();
        assertFalse(drizzle.isApplicable(weather));
    }
}
