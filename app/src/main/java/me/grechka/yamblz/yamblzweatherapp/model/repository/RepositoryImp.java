package me.grechka.yamblz.yamblzweatherapp.model.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.ref.WeakReference;

import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.model.CurrentWeather;
import me.grechka.yamblz.yamblzweatherapp.model.response.CurrentWeatherResponse;
import me.grechka.yamblz.yamblzweatherapp.weather.WeatherPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.grechka.yamblz.yamblzweatherapp.WeatherApp.getWeatherApi;

/**
 * Created by Grechka on 16.07.2017.
 */

public class RepositoryImp implements Repository {
    private CurrentWeather currentWeather;
    private WeatherPresenter presenter;
    private WeakReference<Context> context;

    @Override
    public void setContext(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    public void setPresenter(WeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getCurrentWeather() {
        Call call = getWeatherApi().getCurrentWeather("524901", "ru", "metric", WeatherApp.APIKEY);
        call.enqueue(new Callback<CurrentWeatherResponse>() {

            @Override
            public void onResponse(@NonNull Call<CurrentWeatherResponse> call, @NonNull Response<CurrentWeatherResponse> response) {
                Log.d("retrofit", "Go to Network");
                if (response.body() != null) {
                    Log.d("retrofit", "Got positive answer");
                    CurrentWeatherResponse currentWeatherResponse = response.body();
                    double temperature = currentWeatherResponse.getMain().getTemp();
                    String description = currentWeatherResponse.getWeather().get(0).getDescription();
                    currentWeather = new CurrentWeather(temperature, description);
                    saveCurrentWeather();
                } else {
                    Log.d("retrofit", "Got negative answer");
                }
            }
            @Override
            public void onFailure(@NonNull Call<CurrentWeatherResponse> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void saveCurrentWeather() {
        context.get()
                .getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                .putString("description", currentWeather.description)
                .putString("temperature", Double.toString(currentWeather.temperature)).apply();
        if (presenter != null)
            updateCurrentWeather();
    }

    @Override
    public void updateCurrentWeather() {
        presenter.showCurrentWeather(currentWeather);
    }
}
