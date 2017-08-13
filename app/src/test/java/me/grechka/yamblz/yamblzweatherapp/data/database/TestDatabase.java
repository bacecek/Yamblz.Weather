package me.grechka.yamblz.yamblzweatherapp.data.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.support.annotation.NonNull;

import me.grechka.yamblz.yamblzweatherapp.data.database.daos.CityDao;
import me.grechka.yamblz.yamblzweatherapp.data.database.daos.ForecastDao;
import me.grechka.yamblz.yamblzweatherapp.data.database.daos.WeatherDao;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

/**
 * Created by alexander on 09/08/2017.
 */

public class TestDatabase extends AppDatabase {

    private WeatherDao weatherDao;
    private ForecastDao forecastDao;
    private CityDao cityDao;

    public TestDatabase(@NonNull WeatherDao weatherDao,
                        @NonNull ForecastDao forecastDao,
                        @NonNull CityDao cityDao) {
        this.weatherDao = weatherDao;
        this.forecastDao = forecastDao;
        this.cityDao = cityDao;
    }

    @Override
    public ForecastDao forecastDao() {
        return forecastDao;
    }

    @Override
    public WeatherDao weatherDao() {
        return weatherDao;
    }

    @Override
    public CityDao cityDao() {
        return cityDao;
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
