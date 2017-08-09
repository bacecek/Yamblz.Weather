package me.grechka.yamblz.yamblzweatherapp.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.TestObserver;
import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.data.storages.Storage;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 09/08/2017.
 */

public class PreferencesRepositoryUnitTests extends BaseUnitTest {

    private PreferencesRepository repository;

    @Mock Storage<String> prefsStorage;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        repository = new PreferencesRepositoryImpl(prefsStorage);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();

        when(prefsStorage.getInteger(anyString(), anyInt())).thenReturn(0);
    }

    @Test
    public void PreferencesRepository_getUnits() {
        TestObserver<List<Integer>> observer = repository.getUnits().test();

        List<Integer> expectedUnits = new ArrayList<>();
        expectedUnits.add(0);
        expectedUnits.add(0);
        expectedUnits.add(0);

        observer
                .assertValueCount(1)
                .assertValue(expectedUnits);
    }

    @Test
    public void PreferencesRepository_setFrequency() {
        repository.setFrequency(0);
        verify(prefsStorage).put(anyString(), anyInt());
    }

    @Test
    public void PreferencesRepository_getFrequency() {
        int expectedUnit = 0;
        assertEquals(repository.getFrequency(), expectedUnit);
    }

    @Test
    public void PreferencesRepository_setTemperatureUnits() {
        repository.saveTemperatureUnits(0);
        verify(prefsStorage).put(anyString(), anyInt());
    }

    @Test
    public void PreferencesRepository_getTemperatureUnits() {
        int expectedUnit = 0;
        assertEquals(repository.getTemperatureUnits(), expectedUnit);
    }

    @Test
    public void PreferencesRepository_setPressureUnits() {
        repository.savePressureUnits(0);
        verify(prefsStorage).put(anyString(), anyInt());
    }

    @Test
    public void PreferencesRepository_getPressureUnits() {
        int expectedUnit = 0;
        assertEquals(repository.getPressureUnits(), expectedUnit);
    }

    @Test
    public void PreferencesRepository_setSpeedUnits() {
        repository.saveSpeedUnits(0);
        verify(prefsStorage).put(anyString(), anyInt());
    }

    @Test
    public void PreferencesRepository_getSpeedUnits() {
        int expectedUnit = 0;
        assertEquals(repository.getSpeedUnits(), expectedUnit);
    }
}
