package me.grechka.yamblz.yamblzweatherapp.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.CityEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.WeatherEntity;

/**
 * Created by alexander on 02/08/2017.
 */

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CityEntity entity);

    @Update
    void update(CityEntity entity);

    @Insert
    void insertAll(CityEntity... entities);

    @Query("SELECT * FROM cities WHERE active IS 1")
    Single<CityEntity> findActive();
}
