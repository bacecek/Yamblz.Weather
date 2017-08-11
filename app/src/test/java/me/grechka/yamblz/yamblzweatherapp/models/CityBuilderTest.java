package me.grechka.yamblz.yamblzweatherapp.models;

import org.junit.Test;

import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;

import static junit.framework.Assert.*;

/**
 * Created by alexander on 09/08/2017.
 */

public class CityBuilderTest {

    @Test
    public void CityBuilder_createCityWithBuilder_success() {
        City city = new City.Builder()
                .title("Moscow")
                .placeId("146")
                .extendedTitle("Moscow, Russia")
                .location(new CityLocation(0, 0))
                .isActive(false)
                .build();

        assertEquals(city.getTitle(), "Moscow");
        assertEquals(city.getPlaceId(), "146");
        assertEquals(city.getExtendedTitle(), "Moscow, Russia");
        assertEquals(city.getLocation(), new CityLocation(0, 0));
        assertEquals(city.isActive(), false);
    }

    @Test
    public void CityBuilder_copyCityAndEditWithBuilder_success() {
        City moscow = new City.Builder()
                .title("Moscow")
                .placeId("146")
                .extendedTitle("Moscow, Russia")
                .location(new CityLocation(0, 0))
                .isActive(false)
                .build();

        City city = new City.Builder(moscow)
                .placeId("122")
                .isActive(true)
                .build();

        assertEquals(city.getTitle(), "Moscow");
        assertEquals(city.getPlaceId(), "122");
        assertEquals(city.getExtendedTitle(), "Moscow, Russia");
        assertEquals(city.getLocation(), new CityLocation(0, 0));
        assertEquals(city.isActive(), true);
    }
}
