package me.grechka.yamblz.yamblzweatherapp.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;

/**
 * Created by alexander on 02/08/2017.
 */

@Entity(tableName = "cities", indices =
@Index(value = {"place_id"}, unique = true))
public class CityEntity {

    public static final CityEntity DEFAULT = new CityEntity();

    static {
        DEFAULT.setUid(1);
        DEFAULT.setTitle("Moscow");
        DEFAULT.setArea("Moscow");
        DEFAULT.setLatitude(55.755826);
        DEFAULT.setLongitude(37.6173);
        DEFAULT.setActive(true);
        DEFAULT.setPlaceId("ChIJybDUc_xKtUYRTM9XV8zWRD0");

    }

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "area")
    private String area;

    @ColumnInfo(name = "place_id")
    private String placeId;

    @ColumnInfo(name = "location_latitude")
    private double latitude;

    @ColumnInfo(name = "location_longitude")
    private double longitude;

    @ColumnInfo(name = "active")
    private boolean active = false;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityEntity{");
        sb.append("uid=").append(uid);
        sb.append(", title='").append(title).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", placeId='").append(placeId).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }

    public static City toCity(@NonNull CityEntity entity) {
        CityLocation location = new CityLocation(entity.getLatitude(), entity.getLongitude());

        return new City.Builder()
                .placeId(entity.getPlaceId())
                .title(entity.getTitle())
                .extendedTitle(entity.getArea())
                .location(location)
                .isActive(entity.isActive())
                .build();
    }

    public static CityEntity fromCity(@NonNull City city, boolean isActive) {
        CityEntity entity = new CityEntity();
        entity.setPlaceId(city.getPlaceId());
        entity.setTitle(city.getTitle());
        entity.setArea(city.getExtendedTitle());
        entity.setLatitude(city.getLocation().getLatitude());
        entity.setLongitude(city.getLocation().getLongitude());
        entity.setActive(isActive);
        return entity;
    }
}
