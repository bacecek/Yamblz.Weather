package me.grechka.yamblz.yamblzweatherapp.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.ForecastEntity;

/**
 * Created by alexander on 05/08/2017.
 */

@Dao
public interface ForecastDao {

    @Insert
    void insert(ForecastEntity forecast);

    @Insert
    void insertAll(List<ForecastEntity> forecasts);

    @Query("SELECT * FROM forecasts WHERE city IS :cityId ORDER BY date")
    Single<List<ForecastEntity>> getAll(long cityId);

    @Query("DELETE FROM forecasts")
    void delete();
}
