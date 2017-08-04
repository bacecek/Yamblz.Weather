package me.grechka.yamblz.yamblzweatherapp.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.WeatherEntity;

/**
 * Created by alexander on 02/08/2017.
 */

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WeatherEntity... entities);

    @Query("SELECT * FROM weather WHERE city IS :cityId")
    Single<WeatherEntity> findCurrent(long cityId);

    @Delete
    void delete(WeatherEntity entity);
}
