package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.base.BaseTest;
import me.grechka.yamblz.yamblzweatherapp.domain.errors.MissingCityException;
import me.grechka.yamblz.yamblzweatherapp.domain.weather.WeatherInteractor;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Cloudy;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 11/08/2017.
 */
public class WeatherPresenterTest extends BaseTest {

    private WeatherPresenter presenter;
    private Set<WeatherType> types = new HashSet<>();

    @Mock WeatherView view;

    @Mock RxSchedulers schedulers;
    @Mock WeatherInteractor interactor;

    @Before
    @Override
    public void onInit() {
        super.onInit();
        types.add(new Cloudy());

        presenter = new WeatherPresenter(schedulers,
                interactor, types);
        presenter.attachView(view);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();

        List<Integer> prefs = new ArrayList<>();
        prefs.add(0);
        prefs.add(1);
        prefs.add(0);

        City city = new City.Builder().build();

        when(interactor.getWeather())
                .thenReturn(Single.just(new Weather.Builder().build()));

        when(interactor.getForecast())
                .thenReturn(Single.just(new ArrayList<>()));

        when(interactor.getUnitsMods())
                .thenReturn(Observable.just(prefs));

        when(interactor.getCity())
                .thenReturn(Flowable.just(city));

        when(schedulers.getIoToMainTransformerSingle())
                .thenReturn(s -> s);

        when(schedulers.getIoToMainTransformer())
                .thenReturn(o -> o);

        when(schedulers.getIoToMainTransformerFlowable())
                .thenReturn(f -> f);
    }

    @Test
    public void updateWeather_success() {
        Weather weather = new Weather.Builder().weatherId(805).build();
        when(interactor.updateWeather()).thenReturn(Single.just(weather));

        presenter.updateWeather();

        verify(view, atLeastOnce()).setErrorViewEnabled(false);
        verify(view).setWeather(any(Weather.class), any(WeatherType.class));
    }

    @Test
    public void updateWeather_throwMissingCityException() {
        when(interactor.updateWeather()).thenReturn(Single.fromCallable(() -> {
            throw new MissingCityException();
        }));

        presenter.updateWeather();

        verify(view).hideLoading();
        verify(view).setErrorViewEnabled(true);
        verify(view).onMissingCityError();
    }

    @Test
    public void updateWeather_throwException() {
        when(interactor.updateWeather()).thenReturn(Single.fromCallable(() -> {
            throw new RuntimeException();
        }));

        presenter.updateWeather();

        verify(view).hideLoading();
        verify(view).setErrorViewEnabled(true);
        verify(view).onNetworkError();
    }
}