package me.grechka.yamblz.yamblzweatherapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.grechka.yamblz.yamblzweatherapp.data.storages.Storage;

/**
 * Created by alexander on 08/08/2017.
 */

public class PreferencesRepositoryImpl implements PreferencesRepository {

    private interface OnPrefsChanged {
        void onChanged();
    }

    private Storage<String> prefs;
    @Nullable private OnPrefsChanged listener;
    private Observable<List<Integer>> observable;

    public PreferencesRepositoryImpl(@NonNull Storage<String> prefs) {
        this.prefs = prefs;

        observable = Observable.create(e -> {
                final OnPrefsChanged listener = () -> {
                    e.onNext(getMods());
                };

                setListener(listener);

                e.setCancellable(this::removeListener);
            });
    }

    private void setListener(@NonNull OnPrefsChanged listener) {
        this.listener = listener;
    }

    private void removeListener() {
        this.listener = null;
    }

    private List<Integer> getMods() {
        List<Integer> mods = new ArrayList<>();
        mods.add(getTemperatureUnits());
        mods.add(getPressureUnits());
        mods.add(getSpeedUnits());
        return mods;
    }

    @Override
    public Observable<List<Integer>> getUnits() {
        return observable
                .startWith(getMods());
    }

    @Override
    public void setFrequency(int frequency) {
        prefs.put(FREQUENCY_KEY, frequency);
    }

    @Override
    public int getFrequency() {
        return prefs.getInteger(FREQUENCY_KEY, 60);
    }

    @Override
    public void saveTemperatureUnits(int units) {
        prefs.put(UNITS_TEMPERATURE_PREFS_KEY, units);
        if (listener != null) listener.onChanged();
    }

    @Override
    public int getTemperatureUnits() {
        return prefs.getInteger(UNITS_TEMPERATURE_PREFS_KEY, 0);
    }

    @Override
    public void savePressureUnits(int units) {
        prefs.put(UNITS_PRESSURE_PREFS_KEY, units);
        if (listener != null) listener.onChanged();
    }

    @Override
    public int getPressureUnits() {
        return prefs.getInteger(UNITS_PRESSURE_PREFS_KEY, 0);
    }

    @Override
    public void saveSpeedUnits(int units) {
        prefs.put(UNITS_SPEED_PREFS_KEY, units);
        if (listener != null) listener.onChanged();
    }

    @Override
    public int getSpeedUnits() {
        return prefs.getInteger(UNITS_SPEED_PREFS_KEY, 0);
    }
}
