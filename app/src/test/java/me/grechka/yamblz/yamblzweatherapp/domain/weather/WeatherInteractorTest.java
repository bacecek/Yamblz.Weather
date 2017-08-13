package me.grechka.yamblz.yamblzweatherapp.domain.weather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.data.PreferencesRepository;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.Converter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.KelvinToCelsiusConverter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.KelvinToFahrenheitConverter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.MsToKmHConverter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.PascalToMmHgConverter;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.PRESSURE_CONVERTERS_KEY;
import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.SPEED_CONVERTERS_KEY;
import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.TEMPERATURE_CONVERTERS_KEY;
import static org.mockito.Mockito.*;

/**
 * Created by alexander on 08/08/2017.
 */

public class WeatherInteractorTest extends BaseTest {

    private WeatherInteractor weatherInteractor;

    private Weather weather = new Weather.Builder()
            .weatherId(0)
            .clouds(5)
            .humidity(146)
            .maxTemperature(292.5f)
            .minTemperature(289.3f)
            .temperature(290.0f)
            .pressure(1001.2f)
            .sunRiseTime(10000L)
            .sunSetTime(1000001L)
            .windSpeed(5.5f)
            .updateTime(1123123213L)
            .build();

    @Mock DatabaseRepository databaseRepository;
    @Mock PreferencesRepository preferencesRepository;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        Map<String, List<Converter<Integer, Float>>> converters = new HashMap<>();

        List<Converter<Integer, Float>> temperature = new ArrayList<>();
        temperature.add(new KelvinToCelsiusConverter());
        temperature.add(new KelvinToFahrenheitConverter());

        converters.put(TEMPERATURE_CONVERTERS_KEY, temperature);

        List<Converter<Integer, Float>> pressure = new ArrayList<>();
        pressure.add(new PascalToMmHgConverter());

        converters.put(PRESSURE_CONVERTERS_KEY, pressure);

        List<Converter<Integer, Float>> speed = new ArrayList<>();
        speed.add(new MsToKmHConverter());

        converters.put(SPEED_CONVERTERS_KEY, speed);

        weatherInteractor = new WeatherInteractorImpl(databaseRepository, preferencesRepository, converters);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();

        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);

        when(databaseRepository.getWeather()).thenReturn(Single.just(weather));
        when(databaseRepository.getNetworkWeather()).thenReturn(Single.just(weather));
        when(databaseRepository.getForecast()).thenReturn(Single.just(weatherList));
        when(databaseRepository.getNetworkForecasts()).thenReturn(Single.just(weatherList));

        when(preferencesRepository.getTemperatureUnits()).thenReturn(ConvertersConfig.TEMPERATURE_CELSIUS);
        when(preferencesRepository.getPressureUnits()).thenReturn(ConvertersConfig.PRESSURE_MMHG);
        when(preferencesRepository.getSpeedUnits()).thenReturn(ConvertersConfig.SPEED_MS);
    }

    @Test
    public void WeatherInteractor_getCity() {
        weatherInteractor.getCity();
        verify(databaseRepository).getCity();
    }

    @Test
    public void WeatherInteractor_getWeather_convertersApplied() {
        TestObserver<Weather> observer = weatherInteractor.getWeather().test();

        Weather expectedWeather = new Weather.Builder(weather)
                .temperature(16.850006f)
                .minTemperature(16.149994f)
                .maxTemperature(19.350006f)
                .pressure(750.0f)
                .windSpeed(5.5f)
                .build();

        observer
                .assertValueCount(1)
                .assertValue(expectedWeather);

        verify(databaseRepository).getWeather();
        verify(preferencesRepository, atLeastOnce()).getTemperatureUnits();
        verify(preferencesRepository, atLeastOnce()).getPressureUnits();
        verify(preferencesRepository, atLeastOnce()).getSpeedUnits();
    }

    @Test
    public void WeatherInteractor_updateWeather_convertersApplied() {
        TestObserver<Weather> observer = weatherInteractor.updateWeather().test();

        Weather expectedWeather = new Weather.Builder(weather)
                .temperature(16.850006f)
                .minTemperature(16.149994f)
                .maxTemperature(19.350006f)
                .pressure(750.0f)
                .windSpeed(5.5f)
                .build();

        observer
                .assertValueCount(1)
                .assertValue(expectedWeather);

        verify(databaseRepository).getNetworkWeather();
        verify(preferencesRepository, atLeastOnce()).getTemperatureUnits();
        verify(preferencesRepository, atLeastOnce()).getPressureUnits();
        verify(preferencesRepository, atLeastOnce()).getSpeedUnits();
    }

    @Test
    public void WeatherInteractor_getForecast_convertersApplied() {
        TestObserver<List<Weather>> observer = weatherInteractor.getForecast().test();

        Weather expectedWeather = new Weather.Builder(weather)
                .temperature(16.850006f)
                .build();

        List<Weather> list = new ArrayList<>();
        list.add(expectedWeather);

        observer
                .assertValueCount(1)
                .assertValues(list);

        verify(databaseRepository).getForecast();
        verify(preferencesRepository, atLeastOnce()).getTemperatureUnits();
    }

    @Test
    public void WeatherInteractor_updateForecast_convertersApplied() {
        TestObserver<List<Weather>> observer = weatherInteractor.updateForecast().test();

        Weather expectedWeather = new Weather.Builder(weather)
                .temperature(16.850006f)
                .build();

        List<Weather> list = new ArrayList<>();
        list.add(expectedWeather);

        observer
                .assertValueCount(1)
                .assertValues(list);

        verify(databaseRepository).getNetworkForecasts();
        verify(preferencesRepository, atLeastOnce()).getTemperatureUnits();
    }

    @Test
    public void WeatherInteractor_getUnitsMods() {
        weatherInteractor.getUnitsMods();
        verify(preferencesRepository).getUnits();
    }
}
