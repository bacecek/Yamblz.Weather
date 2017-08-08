package me.grechka.yamblz.yamblzweatherapp.domain.settings;

import javax.inject.Inject;

import me.grechka.yamblz.yamblzweatherapp.data.PreferencesRepository;

/**
 * Created by alexander on 07/08/2017.
 */

public class SettingsInteractorImpl implements SettingsInteractor {

    private PreferencesRepository repository;

    @Inject
    public SettingsInteractorImpl(PreferencesRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getUpdateFrequency() {
        return repository.getFrequency();
    }

    @Override
    public void setUpdateFrequency(int frequency) {
        repository.setFrequency(frequency);
    }

    @Override
    public int getTemperatureUnits() {
        return repository.getTemperatureUnits();
    }

    @Override
    public void setTemperatureUnits(int units) {
        repository.saveTemperatureUnits(units);
    }

    @Override
    public int getSpeedUnits() {
        return repository.getSpeedUnits();
    }

    @Override
    public void setSpeedUnits(int units) {
        repository.saveSpeedUnits(units);
    }

    @Override
    public int getPressureUnits() {
        return repository.getPressureUnits();
    }

    @Override
    public void setPressureUnits(int units) {
        repository.savePressureUnits(units);
    }
}
