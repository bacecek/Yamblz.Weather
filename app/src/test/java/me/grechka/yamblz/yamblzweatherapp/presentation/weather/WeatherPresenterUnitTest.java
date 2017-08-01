package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.repository.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

import static org.mockito.Mockito.*;

/**
 * Created by alexander on 30/07/2017.
 */

public class WeatherPresenterUnitTest extends BaseUnitTest {

    private WeatherPresenter presenter;

    @Mock WeatherView view;
    @Mock
    AppRepository appRepository;
    @Mock RxSchedulers scheduler;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        presenter = new WeatherPresenter(scheduler, appRepository);
        presenter.attachView(view);
    }

    @Override
    public void onMockInit() {
        CurrentWeather item = new CurrentWeather("", "", "", "", "", "");

        when(appRepository.updateCurrentWeather())
                .thenReturn(Single.just(item));

        when(scheduler.getIoToMainTransformerSingle())
                .thenReturn(objectObservable -> objectObservable);
    }

    @Test
    public void weatherPresenter_attachView_citiesWereShowed() {
        verify(view, only()).showCity(any(City.class));
    }

    @Test
    public void weatherPresenter_updateWeather_weaterWereUpdateAndDisplayedCorrectly() {
        presenter.updateCurrentWeather();

        verify(view).showCurrentWeather(anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString());
    }

    @Test
    public void weatherPresenter_getCachedCurrentWeatherWhenWeatherIsInCache_success() {
        CurrentWeather item = new CurrentWeather("12", "", "", "", "", "");

        when(appRepository.getSavedCurrentWeather())
                .thenReturn(Single.just(item));

        presenter.showSavedCurrentWeather();

        verify(view).showCurrentWeather(anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString());
    }

    @Test
    public void weatherPresenter_getCachedCurrentWeatherWhenWeatherNeedToBeDownloaded_wereCalledUpdateWeatherMethod() {
        CurrentWeather item = new CurrentWeather("-", "", "", "", "", "");

        when(appRepository.getSavedCurrentWeather())
                .thenReturn(Single.just(item));

        presenter.showSavedCurrentWeather();

        verify(appRepository).updateCurrentWeather();
        verify(view).showCurrentWeather(anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString());
    }
}
