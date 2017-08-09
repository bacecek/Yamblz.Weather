package me.grechka.yamblz.yamblzweatherapp.data;

import android.database.Cursor;

import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.data.database.AppDatabase;
import me.grechka.yamblz.yamblzweatherapp.data.database.TestDatabase;
import me.grechka.yamblz.yamblzweatherapp.data.database.daos.CityDao;
import me.grechka.yamblz.yamblzweatherapp.data.database.daos.ForecastDao;
import me.grechka.yamblz.yamblzweatherapp.data.database.daos.WeatherDao;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.CityEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.ForecastEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.WeatherEntity;
import me.grechka.yamblz.yamblzweatherapp.helpers.JsonProvider;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.forecast.Forecast;
import me.grechka.yamblz.yamblzweatherapp.models.response.forecast.ForecastResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 09/08/2017.
 */

public class DatabaseRepositoryUnitTests extends BaseUnitTest {

    @Mock NetworkRepository repository;

    @Mock CityDao cityDao;
    @Mock WeatherDao weatherDao;
    @Mock ForecastDao forecastDao;

    AppDatabase database;
    private DatabaseRepository databaseRepository;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        database = new TestDatabase(weatherDao, forecastDao, cityDao);
        databaseRepository = new DatabaseRepositoryImpl(database, repository);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();

        List<CityEntity> cities = new ArrayList<>();
        cities.add(CityEntity.DEFAULT);

        List<ForecastEntity> forecasts = new ArrayList<>();
        forecasts.add(new ForecastEntity());

        when(cityDao.findActive()).thenReturn(Flowable.just(CityEntity.DEFAULT));
        when(cityDao.find()).thenReturn(Flowable.just(cities));
        when(cityDao.findByCoordinates(anyString(),
                anyDouble(), anyDouble())).thenReturn(Single.just(CityEntity.DEFAULT));

        when(weatherDao.findWeatherByCity(anyLong())).thenReturn(Single.just(new WeatherEntity()));

        when(repository.getWeatherByLocation(anyDouble(), anyDouble()))
                .thenReturn(Single.just(new Weather.Builder().build()));

        ForecastResponse forecastsResponse = JsonProvider.openFile(ForecastResponse.class, "openweather-forecast.json");

        when(repository.getForecastByLocation(anyDouble(), anyDouble(), anyString()))
                .thenReturn(Single.just(forecastsResponse));

        when(forecastDao.getAll(anyLong())).thenReturn(Single.just(forecasts));
    }

    @Test
    public void DatabaseRepository_addCity() {
        City city = CityEntity.toCity(CityEntity.DEFAULT);
        databaseRepository.addCity(city).subscribe();

        verify(cityDao).findActive();
        verify(cityDao).insert(any(CityEntity.class));
    }

    @Test
    public void DatabaseRepository_getCity() {
        City expected = CityEntity.toCity(CityEntity.DEFAULT);

        TestSubscriber<City> subscriber = databaseRepository
                .getCity().test();

        subscriber
                .assertValueCount(1)
                .assertValue(expected);
    }

    @Test
    public void DatabaseRepository_markCityAsActive() {
        City city = CityEntity.toCity(CityEntity.DEFAULT);
        databaseRepository.markCityAsActive(city).subscribe();

        verify(cityDao).findByCoordinates(anyString(), anyDouble(), anyDouble());
        verify(cityDao, atLeastOnce()).update(any(CityEntity.class));
    }

    @Test
    public void DatabaseRepository_getWeather() {
        databaseRepository.getWeather();
    }

    @Test
    public void DatabaseRepository_getCachedWeather() {
        databaseRepository.getCachedWeather().subscribe();

        verify(cityDao, atLeastOnce()).findActive();
        verify(weatherDao).findWeatherByCity(anyLong());
    }

    @Test
    public void DatabaseRepository_getNetworkWeather() {
        databaseRepository.getNetworkWeather().subscribe();

        verify(repository).getWeatherByLocation(anyDouble(), anyDouble());
        verify(weatherDao).insert(any(WeatherEntity.class));
    }

    @Test
    public void DatabaseRepository_getCachedForecasts() {
        databaseRepository.getCachedForecasts().subscribe();

        verify(forecastDao).getAll(anyLong());
    }

    @Test
    public void DatabaseRepository_getNetworkForecasts() throws Exception {
        WeatherEntity weather = new WeatherEntity();
        weather.setSunset(0L);
        weather.setSunrise(0L);

        Class<?> cls = databaseRepository.getClass();
        Field field = cls.getDeclaredField("weather");
        field.setAccessible(true);
        field.set(databaseRepository, weather);

        TestObserver<List<Weather>> observer = databaseRepository.getNetworkForecasts().test();

        observer
                .assertValueCount(1);

        verify(repository).getForecastByLocation(anyDouble(), anyDouble(), anyString());
        verify(forecastDao).delete();
        verify(forecastDao, atLeastOnce()).insert(any(ForecastEntity.class));
    }
}
