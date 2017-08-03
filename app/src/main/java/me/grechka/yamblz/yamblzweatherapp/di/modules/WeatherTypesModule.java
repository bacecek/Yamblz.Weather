package me.grechka.yamblz.yamblzweatherapp.di.modules;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.ClearSky;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Cloudy;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Drizzle;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Foggy;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Rainy;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Snowy;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Sunny;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.Thunder;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;

/**
 * Created by alexander on 14/07/2017.
 */

@Module
public class WeatherTypesModule {

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideSunny() {
        return new Sunny();
    }

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideClearSky() {
        return new ClearSky();
    }

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideCloudy() {
        return new Cloudy();
    }

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideThunder() {
        return new Thunder();
    }

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideDrizzle() {
        return new Drizzle();
    }

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideRainy() {
        return new Rainy();
    }

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideSnowy() {
        return new Snowy();
    }

    @Provides
    @IntoSet
    @MainScope
    public WeatherType provideFoggy() {
        return new Foggy();
    }
}
