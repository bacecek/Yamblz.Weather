package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.presentation.forecast.ForecastDiffCallback;

import static org.junit.Assert.*;

/**
 * Created by alexander on 11/08/2017.
 */
public class FavoritesDiffCallbackTest extends BaseTest {

    private FavoritesDiffCallback favoritesDiffCallback;

    private List<City> oldList = new ArrayList<>();
    private List<City> newList = new ArrayList<>();

    @Before
    @Override
    public void onInit() {
        super.onInit();

        oldList.add(
                new City.Builder()
                    .title("Moscow")
                    .placeId("1")
                    .build());

        oldList.add(
                new City.Builder()
                    .title("San Jose")
                        .placeId("0")
                        .build());

        newList.add(
                new City.Builder()
                        .title("Moscow")
                        .placeId("1")
                        .build());

        newList.add(
                new City.Builder()
                        .title("Moscova")
                        .placeId("146")
                        .build());

        favoritesDiffCallback = new FavoritesDiffCallback(oldList, newList);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();
    }

    @Test
    public void assertOldListSize() {
        assertEquals(2, favoritesDiffCallback.getOldListSize());
    }

    @Test
    public void assertNewListSize() {
        assertEquals(2, favoritesDiffCallback.getNewListSize());
    }

    @Test
    public void itemsTheSame_whenPositionIs0() {
        assertTrue(favoritesDiffCallback.areItemsTheSame(0, 0));
    }

    @Test
    public void itemsAreDifferent_whenPositionIs1() {
        assertFalse(favoritesDiffCallback.areItemsTheSame(1, 1));
    }

    @Test
    public void itemsEquals_whenPositionIs0() {
        assertTrue(favoritesDiffCallback.areContentsTheSame(0, 0));
    }

    @Test
    public void itemsNotEquals_whenPositionIs1() {
        assertFalse(favoritesDiffCallback.areContentsTheSame(1, 1));
    }
}