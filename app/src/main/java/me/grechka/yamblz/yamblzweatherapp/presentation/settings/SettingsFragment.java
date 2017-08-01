package me.grechka.yamblz.yamblzweatherapp.presentation.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.events.OnDrawerLocked;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseFragment;

public class SettingsFragment extends BaseFragment implements SettingsView,
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.update_frequency_group) RadioGroup radioGroup;

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
        WeatherApp.getComponent().inject(this);

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

        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle(R.string.main_activity_navigation_settings);

        ((OnDrawerLocked) getActivity())
                .setDrawerEnabled(false);
    }
}
