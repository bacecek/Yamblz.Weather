package me.grechka.yamblz.yamblzweatherapp.models;

import android.support.annotation.NonNull;

import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;
import me.grechka.yamblz.yamblzweatherapp.utils.TextUtils;

/**
 * Created by alexander on 23/07/2017.
 */

public class City {

    private String title;
    private String placeId;
    private String extendedTitle;
    private CityLocation location;

    private City(@NonNull Builder builder) {
        this.title = builder.title;
        this.placeId = builder.placeId;
        this.location = builder.location;
        this.extendedTitle = builder.extendedTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getExtendedTitle() {
        return extendedTitle;
    }

    public CityLocation getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (title != null ? !title.equals(city.title) : city.title != null) return false;
        if (placeId != null ? !placeId.equals(city.placeId) : city.placeId != null) return false;
        if (extendedTitle != null ? !extendedTitle.equals(city.extendedTitle) : city.extendedTitle != null)
            return false;
        return location != null ? location.equals(city.location) : city.location == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (placeId != null ? placeId.hashCode() : 0);
        result = 31 * result + (extendedTitle != null ? extendedTitle.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("title='").append(title).append('\'');
        sb.append(", placeId='").append(placeId).append('\'');
        sb.append(", extendedTitle='").append(extendedTitle).append('\'');
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private String title;
        private String placeId;
        private String extendedTitle;
        private CityLocation location;

        public Builder() {
        }

        public Builder(@NonNull City city) {
            placeId = city.getPlaceId();
            title = city.getTitle();
            extendedTitle = city.getExtendedTitle();
            location = city.getLocation();
        }

        public Builder title(String title) {
            if (TextUtils.isEmpty(title))
                throw new IllegalArgumentException("Title cannot be null or represented by empty string");
            this.title = title;
            return this;
        }

        public Builder placeId(String placeId) {
            if (TextUtils.isEmpty(placeId))
                throw new IllegalArgumentException("Place id cannot be null or represented by empty string");
            this.placeId = placeId;
            return this;
        }

        public Builder extendedTitle(String extendedTitle) {
            if (TextUtils.isEmpty(extendedTitle))
                throw new IllegalArgumentException("Description cannot be null or represented by empty string");
            this.extendedTitle = extendedTitle;
            return this;
        }

        public Builder location(@NonNull CityLocation location) {
            this.location = location;
            return this;
        }

        public City build() {
            return new City(this);
        }
    }
}
