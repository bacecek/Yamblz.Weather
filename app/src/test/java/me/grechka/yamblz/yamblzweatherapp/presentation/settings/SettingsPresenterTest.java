package me.grechka.yamblz.yamblzweatherapp.presentation.settings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.background.WeatherJobUtils;
import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig;
import me.grechka.yamblz.yamblzweatherapp.domain.settings.SettingsInteractor;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 11/08/2017.
 */
public class SettingsPresenterTest extends BaseTest {

    private SettingsPresenter presenter;

    @Mock SettingsView settingsView;

    @Mock WeatherJobUtils utils;
    @Mock SettingsInteractor interactor;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        presenter = new SettingsPresenter(interactor, utils);
        presenter.attachView(settingsView);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();
        when(interactor.getUpdateFrequency()).thenReturn(0);
        when(interactor.getTemperatureUnits()).thenReturn(ConvertersConfig.TEMPERATURE_CELSIUS);
        when(interactor.getSpeedUnits()).thenReturn(ConvertersConfig.SPEED_MS);
        when(interactor.getPressureUnits()).thenReturn(ConvertersConfig.PRESSURE_MMHG);
    }

    @Test
    public void onFirstViewAttach_callHighlightSettings() {
        SettingsPresenter presenter = new SettingsPresenter(interactor, utils);
        SettingsView view = Mockito.mock(SettingsView.class);
        presenter.attachView(view);

        verify(view).highlightSettings();
    }

    @Test
    public void changeUpdateSchedule() throws Exception {
        presenter.changeUpdateSchedule(0);

        verify(interactor).setUpdateFrequency(anyInt());
        verify(utils).rescheduleWeatherJob(anyInt());
    }

    @Test
    public void saveTemperature_celsius() throws Exception {
        presenter.saveTemperature(R.id.content_common_settings_celsius);
        verify(interactor).setTemperatureUnits(ConvertersConfig.TEMPERATURE_CELSIUS);
    }

    @Test
    public void saveTemperature_fahrenheit() throws Exception {
        presenter.saveTemperature(R.id.content_common_settings_fahrenheit);
        verify(interactor).setTemperatureUnits(ConvertersConfig.TEMPERATURE_FAHRENHEIT);
    }

    @Test
    public void saveSpeed_ms() throws Exception {
        presenter.saveSpeed(R.id.content_common_settings_ms);
        verify(interactor).setSpeedUnits(ConvertersConfig.SPEED_MS);
    }

    @Test
    public void saveSpeed_kmh() throws Exception {
        presenter.saveSpeed(R.id.content_common_settings_kmh);
        verify(interactor).setSpeedUnits(ConvertersConfig.SPEED_KMH);
    }

    @Test
    public void savePressure_mmhg() {
        presenter.savePressure(R.id.content_common_settings_mmhg);
        verify(interactor).setPressureUnits(ConvertersConfig.PRESSURE_MMHG);
    }

    @Test
    public void savePressure_pascal() {
        presenter.savePressure(R.id.content_common_settings_pascal);
        verify(interactor).setPressureUnits(ConvertersConfig.PRESSURE_PASCAL);
    }

    @Test
    public void getUpdateFrequency() throws Exception {
        String freq = presenter.getUpdateFrequency();
        assertEquals("0", freq);
    }

    @Test
    public void isCelsius() throws Exception {
        assertTrue(presenter.isCelsius());
    }

    @Test
    public void isMs() throws Exception {
        assertTrue(presenter.isMs());
    }

    @Test
    public void isMmHg() throws Exception {
        assertTrue(presenter.isMmHg());
    }

}