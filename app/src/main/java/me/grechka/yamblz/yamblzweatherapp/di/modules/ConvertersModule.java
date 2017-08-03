package me.grechka.yamblz.yamblzweatherapp.di.modules;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.Converter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.KelvinToCelsiusConverter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.KelvinToFahrenheitConverter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.MsToKmHConverter;
import me.grechka.yamblz.yamblzweatherapp.domain.converters.PascalToMmHgConverter;

import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.PRESSURE_CONVERTERS_KEY;
import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.SPEED_CONVERTERS_KEY;
import static me.grechka.yamblz.yamblzweatherapp.domain.converters.ConvertersConfig.TEMPERATURE_CONVERTERS_KEY;

/**
 * Created by alexander on 15/07/2017.
 */

@Module
public class ConvertersModule {

    @Provides
    @IntoMap
    @StringKey(TEMPERATURE_CONVERTERS_KEY)
    @MainScope
    public List<Converter<Integer, Float>> provideTemperatureConverters() {
        List<Converter<Integer, Float>> list = new ArrayList<>();
        list.add(new KelvinToCelsiusConverter());
        list.add(new KelvinToFahrenheitConverter());
        return list;
    }

    @Provides
    @IntoMap
    @StringKey(PRESSURE_CONVERTERS_KEY)
    @MainScope
    public List<Converter<Integer, Float>> providePressureConverters() {
        List<Converter<Integer, Float>> list = new ArrayList<>();
        list.add(new PascalToMmHgConverter());
        return list;
    }

    @Provides
    @IntoMap
    @StringKey(SPEED_CONVERTERS_KEY)
    @MainScope
    public List<Converter<Integer, Float>> provideSpeedConverters() {
        List<Converter<Integer, Float>> list = new ArrayList<>();
        list.add(new MsToKmHConverter());
        return list;
    }
}
