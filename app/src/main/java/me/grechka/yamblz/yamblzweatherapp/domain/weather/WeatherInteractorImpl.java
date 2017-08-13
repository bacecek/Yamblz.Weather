package me.grechka.yamblz.yamblzweatherapp.domain.weather;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import me.grechka.yamblz.yamblzweatherapp.data.DatabaseRepository;
import me.grechka.yamblz.yamblzweatherapp.data.PreferencesRepository;
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

    private DatabaseRepository databaseRepository;
    private PreferencesRepository preferencesRepository;

    private List<Converter<Integer, Float>> speedConverters;
    private List<Converter<Integer, Float>> pressuresConverters;
    private List<Converter<Integer, Float>> temperatureConverters;

    @Inject
    public WeatherInteractorImpl(@NonNull DatabaseRepository databaseRepository,
                                 @NonNull PreferencesRepository preferencesRepository,
                                 @NonNull Map<String, List<Converter<Integer, Float>>> converters) {
        this.databaseRepository = databaseRepository;
        this.preferencesRepository = preferencesRepository;

        this.speedConverters = converters.get(SPEED_CONVERTERS_KEY);
        this.pressuresConverters = converters.get(PRESSURE_CONVERTERS_KEY);
        this.temperatureConverters = converters.get(TEMPERATURE_CONVERTERS_KEY);
    }

    @Override
    public Flowable<City> getCity() {
        return databaseRepository.getCity();
    }

    @Override
    public Single<Weather> getWeather() {
        return databaseRepository.getWeather()
                .map(convertWeather());
    }

    @Override
    public Single<Weather> updateWeather() {
        return databaseRepository.getNetworkWeather()
                .map(convertWeather());
    }

    @Override
    public Single<List<Weather>> getForecast() {
        return databaseRepository.getForecast()
                .flatMapObservable(Observable::fromIterable)
                .map(convertForecast())
                .toList();
    }

    @Override
    public Single<List<Weather>> updateForecast() {
        return databaseRepository.getNetworkForecasts()
                .flatMapObservable(Observable::fromIterable)
                .map(convertForecast())
                .toList();
    }

    @Override
    public Observable<List<Integer>> getUnitsMods() {
        return preferencesRepository.getUnits();
    }

    private Function<Weather, Weather> convertWeather() {
        return w -> new Weather.Builder(w)
                        .temperature(
                                applyConverter(preferencesRepository.getTemperatureUnits(),
                                        w.getTemperature(), temperatureConverters)
                        )
                        .minTemperature(
                                applyConverter(preferencesRepository.getTemperatureUnits(),
                                        w.getMinTemperature(), temperatureConverters)
                        )
                        .maxTemperature(
                                applyConverter(preferencesRepository.getTemperatureUnits(),
                                        w.getMaxTemperature(), temperatureConverters)
                        )
                        .pressure(
                                (int) applyConverter(preferencesRepository.getPressureUnits(),
                                        w.getPressure(), pressuresConverters)
                        )
                        .windSpeed(
                                applyConverter(preferencesRepository.getSpeedUnits(),
                                        w.getWindSpeed(), speedConverters)
                        )
                        .build();
    }

    private Function<Weather, Weather> convertForecast() {
        return weather -> new Weather.Builder(weather)
                .temperature(
                        applyConverter(preferencesRepository.getTemperatureUnits(),
                                weather.getTemperature(), temperatureConverters)
                )
                .build();
    }

    private float applyConverter(int mode, float value,
                                 @NonNull List<Converter<Integer, Float>> converters) {
        for (Converter<Integer, Float> converter: converters) {
            if (converter.isApplicable(mode)) return converter.convert(value);
        }
        return value;
    }
}
