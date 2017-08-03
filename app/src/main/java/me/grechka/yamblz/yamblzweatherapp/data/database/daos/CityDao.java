package me.grechka.yamblz.yamblzweatherapp.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.CityEntity;

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
    long[] insertAll(CityEntity... entities);

    @Query("SELECT * FROM cities WHERE active IS 1")
    Single<CityEntity> findActive();

    @Query("SELECT * FROM cities")
    Single<List<CityEntity>> find();

    @Query("SELECT * FROM cities WHERE location_latitude IS :lat AND location_longitude IS :lon AND place_id IS :placeId")
    Single<CityEntity> find(@NonNull String placeId, double lat, double lon);
}
