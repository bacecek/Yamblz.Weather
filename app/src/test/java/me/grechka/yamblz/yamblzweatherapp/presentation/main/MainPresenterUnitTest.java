package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.repository.AppRepository;

import static org.mockito.Mockito.*;

/**
 * Created by alexander on 30/07/2017.
 */

@RunWith(JUnit4.class)
public class MainPresenterUnitTest extends BaseUnitTest {

    @Mock MainView view;
    @Mock
    AppRepository appRepository;

    private MainPresenter presenter;

    @Before
    @Override
    public void onInit() {
        super.onInit();
        presenter = new MainPresenter(appRepository);
        presenter.attachView(view);
    }

    @Override
    public void onMockInit() {
        doNothing()
                .when(view)
                .setCityToHeader(any(City.class));

        when(appRepository.getCity()).thenReturn(
                new City.Builder()
                .title("title")
                .extendedTitle("extended")
                .build());
    }

    @Test
    public void mainPresenter_navigateToWeather_success() {
        presenter.showWeather();
        verify(view).showWeather();
    }

    @Test
    public void mainPresenter_navigateToSettings_success() {
        presenter.showSettings();
        verify(view).showSettings();
    }

    @Test
    public void mainPresenter_navigateToAbout_success() {
        presenter.showAbout();
        verify(view).showAbout();
    }

    @Test
    public void mainPresenter_pressBack_success() {
        presenter.goBack();
        verify(view).goBack();
    }

    @Test
    public void mainPresenter_navigateById_success() {
        presenter.navigate(anyInt());
        verify(view).navigate(anyInt());
    }
}
