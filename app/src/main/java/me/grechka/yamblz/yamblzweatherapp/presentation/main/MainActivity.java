package me.grechka.yamblz.yamblzweatherapp.presentation.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import me.grechka.yamblz.yamblzweatherapp.events.OnDismissDialogListener;
import me.grechka.yamblz.yamblzweatherapp.events.OnDrawerLocked;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.presentation.AboutFragment;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseActivity;
import me.grechka.yamblz.yamblzweatherapp.presentation.citySearch.CitySearchFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.favorites.FavoritesFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.settings.SettingsFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.weather.WeatherFragment;

public class MainActivity extends BaseActivity
        implements MainView,
        OnDrawerLocked,
        NavigationView.OnNavigationItemSelectedListener {

    private TextView cityAreaHeaderTextView;
    private TextView cityTitleHeaderTextView;

    private ActionBarDrawerToggle toggle;
    private WeatherFragment weatherFragment;
    private View navigationHeaderView;

    @BindView(R.id.main_activity_drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.main_activity_navigation_view) NavigationView navigationView;

    @InjectPresenter MainPresenter presenter;

    @ProvidePresenter
    public MainPresenter providePresenter() {
        return WeatherApp
                .get(this)
                .getAppComponent()
                .addMainComponent()
                .getMainPresenter();
    }

    @Override
    protected int obtainLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        setTheme(R.style.AppTheme_NoActionBar);
    }

    @Override
    protected void onInit() {
        super.onInit();

        showWeather();
    }

    @Override
    protected void onViewsCreated(Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationHeaderView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        onHeaderInit(navigationHeaderView);
    }


    private void onHeaderInit(@NonNull View headerView) {
        View searchView = headerView.findViewById(R.id.main_activity_choose_city);
        cityTitleHeaderTextView = (TextView) headerView.findViewById(R.id.fragment_weather_header_city_title);
        cityAreaHeaderTextView = (TextView) headerView.findViewById(R.id.fragment_weather_header_city_area);

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_common_group);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorWhite)), 0, s.length(), 0);
        menuItem.setTitle(s);

        searchView.setOnClickListener(v -> showFavorites());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        presenter.navigate(item.getItemId());
        closeDrawer();
        return true;
    }

    @Override
    public void onBackPressed() {
        presenter.goBack();
    }

    @Override
    public void showWeather() {
        weatherFragment = new WeatherFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weatherFragment)
                .commit();
    }

    @Override
    public void showSettings() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showAbout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new AboutFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showFavorites() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FavoritesFragment())
                .addToBackStack(null)
                .commit();
        closeDrawer();
    }

    @Override
    public void navigate(int screenId) {
        switch (screenId) {
            case R.id.nav_settings:
                presenter.showSettings();
                break;
            case R.id.nav_about:
                presenter.showAbout();
                break;
        }
    }

    @Override
    public void setCityToHeader(@NonNull City city) {
        cityTitleHeaderTextView.setText(city.getTitle());
        cityAreaHeaderTextView.setText(city.getExtendedTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        goBack();
        return true;
    }

    @Override
    public void enableDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.setToolbarNavigationClickListener(null);
    }

    @Override
    public void disableDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.setToolbarNavigationClickListener(v -> goBack());
    }

    private boolean closeDrawer() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) return false;
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void goBack() {
        if (!closeDrawer()) super.onBackPressed();
    }
}
