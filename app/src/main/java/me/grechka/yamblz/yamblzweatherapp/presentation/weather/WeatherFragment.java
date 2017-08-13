package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.events.OnErrorListener;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.AdaptiveFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.forecast.ForecastAdapter;
import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;
import me.grechka.yamblz.yamblzweatherapp.utils.MetricsUtils;
import sasd97.java_blog.xyz.richtextview.RichTextView;

public class WeatherFragment extends AdaptiveFragment implements WeatherView,
        SwipeRefreshLayout.OnRefreshListener,
        AppBarLayout.OnOffsetChangedListener {

    @Inject MetricsUtils metricsUtils;
    @Inject Set<WeatherType> weatherTypes;
    @Inject @InjectPresenter WeatherPresenter presenter;

    @BindView(R.id.content_error_root) View errorRoot;
    @BindView(R.id.fragment_weather_content_root) View contentRoot;

    @BindView(R.id.content_weather_icon) RichTextView weatherIcon;
    @BindView(R.id.content_weather_speed_view) TextView speedView;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.content_weather_pressure_view) TextView pressureView;
    @BindView(R.id.content_weather_humidity_view) TextView humidityView;
    @BindView(R.id.content_weather_temperature_view) TextView temperatureView;
    @BindView(R.id.content_forecast_recycler_view) RecyclerView forecastRecyclerView;

    @Nullable @BindView(R.id.fragment_weather_app_bar) AppBarLayout weatherAppBar;

    private OnErrorListener onErrorListener;
    private ForecastAdapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @ProvidePresenter
    public WeatherPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected int obtainLayoutView() {
        return R.layout.fragment_weather;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WeatherApp
                .get(context)
                .getAppComponent()
                .addMainComponent()
                .inject(this);
    }

    @Override
    protected void onPortrait() {
        super.onPortrait();
        if (metricsUtils.isPhone()) {
            layoutManager = new LinearLayoutManager(getContext());
            return;
        }

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
    }

    @Override
    protected void onLandscape() {
        super.onLandscape();
        layoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);
        forecastAdapter = new ForecastAdapter(weatherTypes);
        forecastRecyclerView.setAdapter(forecastAdapter);
        forecastRecyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(this);
        if (weatherAppBar != null) weatherAppBar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    @Override
    public void addForecasts(List<Weather> forecast) {
        forecastAdapter.addAll(forecast);
    }

    @Override
    public void setWeather(@NonNull Weather weather,
                           @NonNull WeatherType type) {
        forecastAdapter.setTempUnits(getTempUnits());
        weatherIcon.setText(getString(type.getIconRes()));

        temperatureView.setText(getString(R.string.weather_fragment_temperature,
                weather.getTemperature(), getTempUnits()));

        humidityView.setText(getString(R.string.weather_fragment_humidity, weather.getHumidity()));
        pressureView.setText(getString(R.string.weather_fragment_pressure,
                weather.getPressure(), getPressureUnits()));
        speedView.setText(getString(R.string.weather_fragment_wind_speed,
                weather.getWindSpeed(), getSpeedUnits()));

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showCity(@NonNull City city) {
        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle(city.getTitle());
    }

    @Override
    public void onRefresh() {
        presenter.updateWeather();
        presenter.updateForecast();
    }

    @Override
    public void onMissingCityError() {
        if (onErrorListener == null) return;
        onErrorListener.onMissingCity();
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setErrorViewEnabled(boolean isEnabled) {
        errorRoot.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        contentRoot.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.selectBurgerButtonNavigation();
        onErrorListener = mainActivity;
    }

    private String getTempUnits() {
        return presenter.isCelsius() ? getString(R.string.all_weather_celsius)
                : getString(R.string.all_weather_fahrenheit);
    }

    private String getSpeedUnits() {
        return  presenter.isMs() ? getString(R.string.all_weather_ms)
                : getString(R.string.all_weather_kmh);
    }

    private String getPressureUnits() {
        return presenter.isMmHg() ? getString(R.string.all_weather_mmhg)
                : getString(R.string.all_weather_pascal);
    }
}
