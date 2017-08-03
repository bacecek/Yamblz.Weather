package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.events.OnDrawerLocked;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.forecast.ForecastAdapter;
import sasd97.java_blog.xyz.richtextview.RichTextView;

public class WeatherFragment extends BaseFragment implements WeatherView,
        SwipeRefreshLayout.OnRefreshListener {

    private ForecastAdapter forecastAdapter;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.content_weather_icon) RichTextView weatherIcon;
    @BindView(R.id.content_weather_temperature_view) TextView temperatureView;
    @BindView(R.id.content_weather_humidity_view) TextView humidityView;
    @BindView(R.id.content_weather_pressure_view) TextView pressureView;
    @BindView(R.id.content_weather_speed_view) TextView speedView;
    @BindView(R.id.content_forecast_recycler_view) RecyclerView forecastRecyclerView;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    @InjectPresenter
    WeatherPresenter presenter;

    @Inject Set<WeatherType> weatherTypes;

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
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);

        forecastAdapter = new ForecastAdapter(weatherTypes);
        forecastRecyclerView.setAdapter(forecastAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        forecastRecyclerView.setLayoutManager(linearLayoutManager);
        forecastRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void addForecast(Weather weather) {
        forecastAdapter.add(weather);
    }

    @Override
    public void setWeather(@NonNull Weather weather,
                                   @NonNull WeatherType type) {
        weatherIcon.setText(getString(type.getIconRes()));
        temperatureView.setText(String.valueOf(weather.getTemperature()));
        humidityView.setText(String.valueOf(weather.getHumidity()));
        pressureView.setText(String.valueOf(weather.getPressure()));
        speedView.setText(String.valueOf(weather.getWindSpeed()));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showCity(@NonNull City city) {
//        cityTitleTextView.setText(city.getTitle());
    }

    public void onDialogDismissed() {
        presenter.updateCity();
        presenter.updateWeather();
    }

    @Override
    public void showMessage(String message) {
        if (message.equals("Error")) message = getResources().getString(R.string.error);
        else if (message.equals("No network")) message = getResources().getString(R.string.no_network);
        swipeRefreshLayout.setRefreshing(false);
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    @Override
    public void onRefresh() {
        presenter.updateWeather();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle(R.string.main_activity_navigation_weather);

        ((OnDrawerLocked) getActivity())
                .setDrawerEnabled(true);
    }
}
