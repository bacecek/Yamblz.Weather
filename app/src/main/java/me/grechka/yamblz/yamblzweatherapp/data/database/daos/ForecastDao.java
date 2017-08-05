package me.grechka.yamblz.yamblzweatherapp.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.ForecastEntity;

/**
 * Created by alexander on 05/08/2017.
 */

@Dao
public interface ForecastDao {

    @Insert
    void insert(ForecastEntity forecast);

    @Query("SELECT * FROM forecasts WHERE city IS :cityId")
    Single<List<ForecastEntity>> getAll(long cityId);

    @Query("DELETE FROM forecasts")
    void delete();
}
