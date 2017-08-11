package me.grechka.yamblz.yamblzweatherapp.presentation.forecast;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static org.junit.Assert.*;

/**
 * Created by alexander on 11/08/2017.
 */
public class ForecastDiffCallbackTest extends BaseTest {

    private ForecastDiffCallback forecastDiffCallback;

    private List<Weather> oldList = new ArrayList<>();
    private List<Weather> newList = new ArrayList<>();

    @Before
    @Override
    public void onInit() {
        super.onInit();

        oldList.add(
                new Weather.Builder()
                        .temperature(10.0f)
                        .weatherId(800)
                        .humidity(90)
                        .build());

        oldList.add(
                new Weather.Builder()
                        .temperature(12.0f)
                        .weatherId(805)
                        .build());

        newList.add(
                new Weather.Builder()
                        .temperature(10.0f)
                        .weatherId(800)
                        .humidity(90)
                        .build());

        newList.add(
                new Weather.Builder()
                        .temperature(11.0f)
                        .weatherId(800)
                        .humidity(90)
                        .build());

        forecastDiffCallback = new ForecastDiffCallback(oldList, newList);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();
    }

    @Test
    public void assertOldListSize() {
        assertEquals(2, forecastDiffCallback.getOldListSize());
    }

    @Test
    public void assertNewListSize() {
        assertEquals(2, forecastDiffCallback.getNewListSize());
    }

    @Test
    public void itemsTheSame_whenPositionIs0() {
        assertTrue(forecastDiffCallback.areItemsTheSame(0, 0));
    }

    @Test
    public void itemsAreDifferent_whenPositionIs1() {
        assertFalse(forecastDiffCallback.areItemsTheSame(1, 1));
    }

    @Test
    public void itemsEquals_whenPositionIs0() {
        assertTrue(forecastDiffCallback.areContentsTheSame(0, 0));
    }

    @Test
    public void itemsNotEquals_whenPositionIs1() {
        assertFalse(forecastDiffCallback.areContentsTheSame(1, 1));
    }
}