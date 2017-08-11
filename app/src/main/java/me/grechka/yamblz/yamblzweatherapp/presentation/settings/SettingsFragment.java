package me.grechka.yamblz.yamblzweatherapp.presentation.settings;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;

public class SettingsFragment extends BaseFragment implements SettingsView,
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.update_frequency_group) RadioGroup radioGroup;

    @BindColor(R.color.colorTextPrimary) int ordinaryTextColor;
    @BindColor(R.color.colorTextSecondary) int highlightTextColor;
    @BindColor(R.color.colorPrimaryDark) int highlightBackgroundColor;

    @BindViews({R.id.content_common_settings_celsius, R.id.content_common_settings_fahrenheit})
    List<Button> temperatureButtonsGroup;

    @BindViews({R.id.content_common_settings_ms, R.id.content_common_settings_kmh})
    List<Button> speedButtonsGroup;

    @BindViews({R.id.content_common_settings_pascal, R.id.content_common_settings_mmhg})
    List<Button> pressureButtonsGroup;

    @Inject
    @InjectPresenter
    SettingsPresenter presenter;

    @ProvidePresenter
    public SettingsPresenter presenter() {
        return presenter;
    }

    @Override
    protected int obtainLayoutView() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WeatherApp
                .get(context)
                .getAppComponent()
                .addMainComponent()
                .inject(this);

    }

    @Override
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);
        radioGroup.setOnCheckedChangeListener(this);
        setCheckedFrequency();
    }

    private void setCheckedFrequency() {
        String tag = presenter.getUpdateFrequency();
        RadioButton button = (RadioButton) radioGroup.findViewWithTag(tag);
        button.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int frequency = getFrequencyFromTag(checkedId);
        presenter.changeUpdateSchedule(frequency);
    }

    private int getFrequencyFromTag(@IdRes int checkedId) {
        return Integer.parseInt((String) getView().findViewById(checkedId).getTag());
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity
                .getSupportActionBar()
                .setTitle(R.string.main_activity_navigation_settings);

        mainActivity
                .selectBurgerButtonNavigation();
    }


    @OnClick({R.id.content_common_settings_celsius, R.id.content_common_settings_fahrenheit})
    public void onTemperatureGroupClick(View v) {
        deselectGroup(temperatureButtonsGroup);
        enableButton(v.getId(), temperatureButtonsGroup);
        presenter.saveTemperature(v.getId());
    }

    @Override
    public void highlightSettings() {
        enableButton(presenter.isCelsius() ? R.id.content_common_settings_celsius :
                R.id.content_common_settings_fahrenheit, temperatureButtonsGroup);

        enableButton(presenter.isMs() ? R.id.content_common_settings_ms :
                R.id.content_common_settings_kmh, speedButtonsGroup);

        enableButton(presenter.isMmHg() ? R.id.content_common_settings_mmhg :
                R.id.content_common_settings_pascal, pressureButtonsGroup);
    }

    @OnClick({R.id.content_common_settings_ms, R.id.content_common_settings_kmh})
    public void onSpeedGroupClick(View v) {
        deselectGroup(speedButtonsGroup);
        enableButton(v.getId(), speedButtonsGroup);
        presenter.saveSpeed(v.getId());
    }

    @OnClick({R.id.content_common_settings_pascal, R.id.content_common_settings_mmhg})
    public void onPressureGroupClick(View v) {
        deselectGroup(pressureButtonsGroup);
        enableButton(v.getId(), pressureButtonsGroup);
        presenter.savePressure(v.getId());
    }

    private void deselectGroup(List<Button> group) {
        for (Button button : group) {
            button.setTextColor(ordinaryTextColor);
            button.getBackground().clearColorFilter();
        }
    }

    private void enableButton(int buttonId, List<Button> group) {
        for (Button button : group) {
            if (button.getId() != buttonId) continue;
            button.setTextColor(highlightTextColor);
            button.getBackground().setColorFilter(highlightBackgroundColor, PorterDuff.Mode.MULTIPLY);
        }
    }
}
