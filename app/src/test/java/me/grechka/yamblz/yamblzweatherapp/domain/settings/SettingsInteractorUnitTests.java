package me.grechka.yamblz.yamblzweatherapp.domain.settings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.data.PreferencesRepository;

import static org.mockito.Mockito.*;

/**
 * Created by alexander on 08/08/2017.
 */

public class SettingsInteractorUnitTests extends BaseUnitTest {

    private SettingsInteractor settingsInteractor;

    @Mock PreferencesRepository repository;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        settingsInteractor = new SettingsInteractorImpl(repository);
    }

    @Test
    public void SettingsInteractor_getUpdateFrequency() {
        settingsInteractor.getUpdateFrequency();
        verify(repository).getFrequency();
    }

    @Test
    public void SettingsInteractor_setUpdateFrequency() {
        settingsInteractor.setUpdateFrequency(anyInt());
        verify(repository).setFrequency(anyInt());
    }

    @Test
    public void SettingsInteractor_getTemperature() {
        settingsInteractor.getTemperatureUnits();
        verify(repository).getTemperatureUnits();
    }

    @Test
    public void SettingsInteractor_setTemperature() {
        settingsInteractor.setTemperatureUnits(anyInt());
        verify(repository).saveTemperatureUnits(anyInt());
    }

    @Test
    public void SettingsInteractor_getSpeed() {
        settingsInteractor.getSpeedUnits();
        verify(repository).getSpeedUnits();
    }

    @Test
    public void SettingsInteractor_setSpeed() {
        settingsInteractor.setSpeedUnits(anyInt());
        verify(repository).saveSpeedUnits(anyInt());
    }

    @Test
    public void SettingsInteractor_getPressureUnits() {
        settingsInteractor.getPressureUnits();
        verify(repository).getPressureUnits();
    }

    @Test
    public void SettingsInteractor_setPressureUnits() {
        settingsInteractor.setPressureUnits(anyInt());
        verify(repository).savePressureUnits(anyInt());
    }
}
