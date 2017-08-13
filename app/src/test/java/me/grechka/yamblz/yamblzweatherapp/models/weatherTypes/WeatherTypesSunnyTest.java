package me.grechka.yamblz.yamblzweatherapp.models.weatherTypes;

import org.junit.Test;
import java.util.Date;

import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static junit.framework.Assert.*;

/**
 * Created by alexander on 09/08/2017.
 */

public class WeatherTypesSunnyTest {

    @Test
    public void WeatherTypes_sunnyIsApplicable_whenWeatherIdIs800AndTimeIsRight() {
        long currentTime = new Date().getTime();

        Weather weather = new Weather.Builder()
                .weatherId(800)
                .sunRiseTime(currentTime - 1000)
                .sunSetTime(currentTime + 1000)
                .build();

        WeatherType sunny = new Sunny();
        assertTrue(sunny.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_sunnyNotApplicable_whenWeatherIdIs800AndTimeIsNotRight() {
        long currentTime = new Date().getTime();

        Weather weather = new Weather.Builder()
                .weatherId(800)
                .sunRiseTime(currentTime + 2000)
                .sunSetTime(currentTime + 3000)
                .build();

        WeatherType sunny = new Sunny();
        assertFalse(sunny.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_sunnyApplicable_whenWeatherIdIs800AndScheduleIsTurnOff() {
        Weather weather = new Weather.Builder()
                .weatherId(800)
                .build();

        WeatherType sunny = new Sunny();
        assertTrue(sunny.isApplicable(weather));
    }

    @Test
    public void WeatherTypes_sunnyNotApplicable_whenWeatherIdIs805() {
        Weather weather = new Weather.Builder()
                .weatherId(805)
                .build();

        WeatherType sunny = new Sunny();
        assertFalse(sunny.isApplicable(weather));
    }
}
