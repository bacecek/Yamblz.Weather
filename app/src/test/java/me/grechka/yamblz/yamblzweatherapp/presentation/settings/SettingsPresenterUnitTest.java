package me.grechka.yamblz.yamblzweatherapp.presentation.settings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.background.WeatherJobUtils;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by alexander on 30/07/2017.
 */

public class SettingsPresenterUnitTest extends BaseUnitTest {

    private SettingsPresenter presenter;

    @Mock SettingsView view;
    @Mock WeatherJobUtils utils;
    @Mock
    PreferencesProvider preferencesProvider;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        presenter = new SettingsPresenter(utils, preferencesProvider);
        presenter.attachView(view);
    }

    @Override
    public void onMockInit() {
    }

    @Test
    public void settingsPresenter_updateScheduleInterval_intervalSuccessfullySavedToPrefsAndJobUpdated() {
        presenter.changeUpdateSchedule(anyInt());

        verify(preferencesProvider).putUpdateFrequency(anyString());
        verify(utils).rescheduleWeatherJob(anyInt());
    }

    @Test
    public void SettingsPresenter_obtainFrequency_getFrequencyFromSavedLocation() {
        presenter.getUpdateFrequency();

        verify(preferencesProvider).getUpdateFrequency();
    }
}
