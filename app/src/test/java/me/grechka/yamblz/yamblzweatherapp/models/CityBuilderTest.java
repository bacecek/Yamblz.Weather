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

    @Test(expected = IllegalArgumentException.class)
    public void CityBuilder_wrongTitlePassed_throwAnException() {
        new City.Builder()
                .title("   ")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void CityBuilder_wrongExtendedTitlePassed_throwAnException() {
        new City.Builder()
                .extendedTitle("   ")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void CityBuilder_wrongPlaceIdPassed_throwAnException() {
        new City.Builder()
                .placeId("   ")
                .build();
    }

    @Test
    public void CityBuilder_notEqualsToAnother() {
        City moscow = new City.Builder()
                .title("Moscow")
                .placeId("146")
                .extendedTitle("Moscow, Russia")
                .location(new CityLocation(0, 0))
                .isActive(false)
                .build();

        City moscova = new City.Builder(moscow)
                .placeId("122")
                .isActive(true)
                .build();

        assertFalse(moscow.equals(moscova) || moscova.equals(moscow));
        assertNotSame(moscow.hashCode(), moscova.hashCode());
        assertNotSame(moscow.toString(), moscova.toString());
    }

    @Test
    public void CityBuilder_equalsToAnother() {
        City one = new City.Builder()
                .title("Moscow")
                .placeId("146")
                .extendedTitle("Moscow, Russia")
                .location(new CityLocation(0, 0))
                .isActive(false)
                .build();

        City another = new City.Builder(one)
                .build();

        assertTrue(one.equals(another) && another.equals(one));
        assertEquals(one.hashCode(), another.hashCode());
        assertEquals(one.toString(), another.toString());
    }
}
