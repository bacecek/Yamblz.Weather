package me.grechka.yamblz.yamblzweatherapp.presentation.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.events.OnDrawerLocked;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseFragment;

public class WeatherFragment extends BaseFragment implements WeatherView,
        SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.cur_temp) TextView tempView;
    @BindView(R.id.temp_max) TextView maxTempView;
    @BindView(R.id.temp_min) TextView minTempView;
    @BindView(R.id.description) TextView descView;
    @BindView(R.id.wind_value) TextView windView;
    @BindView(R.id.humidity_value) TextView humidityView;
    @BindView(R.id.city) TextView cityTitleTextView;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    @InjectPresenter
    WeatherPresenter presenter;

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
        WeatherApp.getComponent().inject(this);
    }

    @Override
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.showSavedCurrentWeather();
    }

    @Override
    public void showCurrentWeather(String temperature,
                                   String description,
                                   String humidity,
                                   String tempMin,
                                   String tempMax,
                                   String wind) {
        tempView.setText(temperature);
        maxTempView.setText(tempMax);
        minTempView.setText(tempMin);
        descView.setText(description);
        windView.setText(wind);
        humidityView.setText(humidity);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showCity(@NonNull City city) {
        cityTitleTextView.setText(city.getTitle());
    }

    public void onDialogDismissed() {
        presenter.updateCity();
        presenter.updateCurrentWeather();
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
        presenter.updateCurrentWeather();
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
