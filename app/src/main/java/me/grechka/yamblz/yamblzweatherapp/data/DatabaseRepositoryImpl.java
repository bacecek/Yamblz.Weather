package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import me.grechka.yamblz.yamblzweatherapp.data.database.AppDatabase;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.CityEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.ForecastEntity;
import me.grechka.yamblz.yamblzweatherapp.data.database.entities.WeatherEntity;
import me.grechka.yamblz.yamblzweatherapp.domain.errors.MissingCityException;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.response.city.CityLocation;

import static me.grechka.yamblz.yamblzweatherapp.data.net.WeatherApi.API_KEY;

/**
 * Created by alexander on 08/08/2017.
 */

public class DatabaseRepositoryImpl implements DatabaseRepository {

    private AppDatabase database;
    private NetworkRepository networkRepository;

    private CityEntity city;
    private WeatherEntity weather;

    public DatabaseRepositoryImpl(@NonNull AppDatabase database,
                                  @NonNull NetworkRepository networkRepository) {
        this.database = database;
        this.networkRepository = networkRepository;

        onInit();
    }

    private void onInit() {
        this.database.cityDao().findActive()
                .subscribeOn(Schedulers.io())
                .subscribe(city -> this.city = city);
    }

    @Override
    public Completable addCity(@NonNull City city) {
        return Completable
                .fromAction(() -> this.database
                        .cityDao()
                        .insert(CityEntity.fromCity(city, false)));
    }

    @Override
    public Flowable<City> getCity() {
        return database.cityDao().findActive()
                .map(CityEntity::toCity);
    }

    @Override
    public Flowable<List<City>> getCities() {
        return database.cityDao().find()
                .flatMapSingle(cities -> Flowable.fromIterable(cities)
                        .map(CityEntity::toCity)
                        .toList());
    }

    @Override
    public Completable removeCity(@NonNull City city) {
        return Completable.fromAction(() ->
                database.cityDao().delete(city.getPlaceId(),
                        city.getLocation().getLatitude(), city.getLocation().getLongitude()));
    }

    @Override
    public Completable markCityAsActive(@NonNull City city) {
        CityLocation location = city.getLocation();

        CityEntity inactiveCity = this.city == null ? CityEntity.DEFAULT : this.city;
        inactiveCity.setActive(false);

        return database.cityDao()
                .findByCoordinates(city.getPlaceId(),
                        location.getLatitude(), location.getLongitude())
                .flatMapCompletable(newEntity -> {
                    newEntity.setActive(true);
                    this.city = newEntity;

                    return Completable
                            .fromAction(() -> {
                                if (inactiveCity != CityEntity.DEFAULT)
                                    this.database.cityDao().update(inactiveCity);
                                this.database.cityDao().update(newEntity);
                            });
                });
    }

    @Override
    public Single<Weather> getWeather() {
        return this.getCachedWeather()
                .onErrorResumeNext(this.getNetworkWeather());
    }

    @Override
    public Single<Weather> getCachedWeather() {
        return database.cityDao()
                .findActive()
                .flatMapSingle(city -> {
                    this.city = city;
                    return database.weatherDao().findWeatherByCity(city.getUid());
                })
                .map(weather -> {
                    this.weather = weather;
                    return WeatherEntity.toWeather(weather);
                })
                .firstOrError();
    }

    @Override
    public Single<Weather> getNetworkWeather() {
        return Single.fromCallable(() -> {
                    if (city == null) throw new MissingCityException();
                    return city;
                })
                .flatMap(city -> this.networkRepository.getWeatherByLocation(city.getLatitude(), city.getLongitude()))
                .doOnSuccess(weather -> {
                    this.weather = WeatherEntity.fromWeather(weather, city, false);
                    database.weatherDao().insert(this.weather);
                });
    }

    @Override
    public Single<List<Weather>> getForecast() {
        return this.getCachedForecasts()
                .flatMap(list -> {
                    if (list.isEmpty()) return this.getNetworkForecasts();
                    return Single.just(list);
                });
    }

    @Override
    public Single<List<Weather>> getCachedForecasts() {
        return Single.fromCallable(() -> {
                    if (city == null) throw new MissingCityException();
                    return city;
                })
                .flatMap(city -> database.forecastDao().getAll(this.city.getUid()))
                .flatMap(list -> Observable.fromIterable(list)
                        .map(ForecastEntity::toWeather).toList());
    }

    @Override
    public Single<List<Weather>> getNetworkForecasts() {
        return Single.fromCallable(() -> {
                    if (city == null) throw new MissingCityException();
                    return city;
                })
                .flatMap(city -> this.networkRepository.getForecastByLocation(city.getLatitude(), city.getLongitude(), API_KEY))
                .doOnSuccess(forecasts -> database.forecastDao().delete())
                .flatMap(forecast -> Observable.fromIterable(forecast.getForecastList())
                        .map(weather -> new Weather.Builder()
                                .weatherId(weather.getWeather().get(0).getId())
                                .temperature(weather.getTemp().getDay())
                                .updateTime(TimeUnit.SECONDS.toMillis(weather.getDt()))
                                .build())
                        .toList())
                .doOnSuccess(forecast -> Observable.fromIterable(forecast)
                        .map(weather -> ForecastEntity.fromWeather(weather, this.city))
                        .toList()
                        .doOnSuccess(database.forecastDao()::insertAll)
                        .subscribe());
    }
}
