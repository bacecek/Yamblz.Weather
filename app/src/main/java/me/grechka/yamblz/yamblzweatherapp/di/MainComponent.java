package me.grechka.yamblz.yamblzweatherapp.di;

import dagger.Subcomponent;
import me.grechka.yamblz.yamblzweatherapp.di.modules.ConvertersModule;
import me.grechka.yamblz.yamblzweatherapp.di.modules.WeatherTypesModule;
import me.grechka.yamblz.yamblzweatherapp.di.scopes.MainScope;
import me.grechka.yamblz.yamblzweatherapp.presentation.citySearch.CitySearchFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.favorites.FavoritesPresenter;
import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainPresenter;
import me.grechka.yamblz.yamblzweatherapp.presentation.settings.SettingsFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.settings.SettingsPresenter;
import me.grechka.yamblz.yamblzweatherapp.presentation.weather.WeatherFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.weather.WeatherPresenter;

/**
 * Created by alexander on 03/08/2017.
 */

@Subcomponent(modules = {WeatherTypesModule.class, ConvertersModule.class})
@MainScope
public interface MainComponent {
    void inject(CitySearchFragment fragment);
    void inject(WeatherFragment weatherFragment);
    void inject(SettingsFragment settingsFragment);

    MainPresenter getMainPresenter();

    WeatherPresenter getWeatherPresenter();
    SettingsPresenter getSettingsPresenter();
    FavoritesPresenter getFavoritesPresenter();
}
