package me.grechka.yamblz.yamblzweatherapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.grechka.yamblz.yamblzweatherapp.data.database.daos.CityDao;
import me.grechka.yamblz.yamblzweatherapp.data.database.daos.WeatherDao;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.CityEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.ForecastEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.WeatherEntity;

/**
 * Created by alexander on 02/08/2017.
 */

@Database(entities = {WeatherEntity.class, CityEntity.class, ForecastEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
    public abstract CityDao cityDao();
}
