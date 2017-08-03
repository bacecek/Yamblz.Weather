package me.grechka.yamblz.yamblzweatherapp.domain;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.data.AppRepository;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.Converter;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;

import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.PRESSURE_CONVERTERS_KEY;
import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.SPEED_CONVERTERS_KEY;
import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.TEMPERATURE_CONVERTERS_KEY;

/**
 * Created by Grechka on 19.07.2017.
 */

@MainScope
public class WeatherInteractorImpl implements WeatherInteractor {

    private AppRepository repository;
    private List<Converter<Integer, Float>> speedConverters;
    private List<Converter<Integer, Float>> pressuresConverters;
    private List<Converter<Integer, Float>> temperatureConverters;

    @Inject
    public WeatherInteractorImpl(@NonNull AppRepository repository,
                                 @NonNull Map<String, List<Converter<Integer, Float>>> converters) {
        this.repository = repository;
        this.speedConverters = converters.get(SPEED_CONVERTERS_KEY);
        this.pressuresConverters = converters.get(PRESSURE_CONVERTERS_KEY);
        this.temperatureConverters = converters.get(TEMPERATURE_CONVERTERS_KEY);
    }

    @Override
    public Flowable<City> getCity() {
        return repository.getCity();
    }

    @Override
    public Single<Weather> updateWeather() {
        return convertModel(repository.updateWeather());
    }

    @Override
    public Flowable<Weather> getCachedWeather() {
        return repository.getCachedWeather()
                .map(w -> new Weather.Builder(w)
                        .temperature(
                                applyConverter(repository.getTemperatureUnits(),
                                        w.getTemperature(), temperatureConverters)
                        )
                        .minTemperature(
                                applyConverter(repository.getTemperatureUnits(),
                                        w.getMinTemperature(), temperatureConverters)
                        )
                        .maxTemperature(
                                applyConverter(repository.getTemperatureUnits(),
                                        w.getMaxTemperature(), temperatureConverters)
                        )
                        .pressure(
                                (int) applyConverter(repository.getPressureUnits(),
                                        w.getPressure(), pressuresConverters)
                        )
                        .windSpeed(
                                applyConverter(repository.getSpeedUnits(),
                                        w.getWindSpeed(), speedConverters)
                        )
                        .build());
    }

    @Override
    public Observable<Weather> getForecast() {
        return repository.getForecast()
                .map(weather -> new Weather.Builder(weather)
                                .temperature(
                                        applyConverter(repository.getTemperatureUnits(),
                                                weather.getTemperature(), temperatureConverters)
                                )
                .build());
    }

    @Override
    public int[] getModes() {
        return new int[]{
                repository.getTemperatureUnits(),
                repository.getPressureUnits(),
                repository.getSpeedUnits()
        };
    }

    private Single<Weather> convertModel(Single<Weather> weatherObservable) {
        return weatherObservable
                .map(w -> new Weather.Builder(w)
                        .temperature(
                                applyConverter(repository.getTemperatureUnits(),
                                        w.getTemperature(), temperatureConverters)
                        )
                        .minTemperature(
                                applyConverter(repository.getTemperatureUnits(),
                                        w.getMinTemperature(), temperatureConverters)
                        )
                        .maxTemperature(
                                applyConverter(repository.getTemperatureUnits(),
                                        w.getMaxTemperature(), temperatureConverters)
                        )
                        .pressure(
                                (int) applyConverter(repository.getPressureUnits(),
                                        w.getPressure(), pressuresConverters)
                        )
                        .windSpeed(
                                applyConverter(repository.getSpeedUnits(),
                                        w.getWindSpeed(), speedConverters)
                        )
                        .build());
    }

    private float applyConverter(int mode, float value,
                                 @NonNull List<Converter<Integer, Float>> converters) {
        for (Converter<Integer, Float> converter: converters) {
            if (converter.isApplicable(mode)) return converter.convert(value);
        }
        return value;
    }
}
