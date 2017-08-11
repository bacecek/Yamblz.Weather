package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.events.OnDismissDialogListener;
import me.grechka.yamblz.yamblzweatherapp.events.OnDrawerLocked;
import me.grechka.yamblz.yamblzweatherapp.events.OnItemClickListener;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.AdaptiveFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.citySearch.CitySearchFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;

/**
 * Created by alexander on 03/08/2017.
 */

public class FavoritesFragment extends AdaptiveFragment
        implements FavoritesView,
        OnItemClickListener<City>,
        OnDismissDialogListener {

    private OnDrawerLocked drawerLocker;
    private FavoritesAdapter favoritesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.fragment_favorites_empty_view) View emptyView;
    @BindView(R.id.fragment_favorites_recycler_view) RecyclerView citiesRecyclerView;

    @InjectPresenter FavoritesPresenter presenter;

    @ProvidePresenter FavoritesPresenter providePresenter() {
        return WeatherApp.get(getContext())
                .getAppComponent()
                .addMainComponent()
                .getFavoritesPresenter();
    }

    @Override
    protected int obtainLayoutView() {
        return R.layout.fragment_favorites;
    }

    @Override
    protected void onPortrait() {
        super.onPortrait();
        layoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    protected void onLandscape() {
        super.onLandscape();
        layoutManager = new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);
        initDrawer();

        favoritesAdapter = new FavoritesAdapter();

        ItemTouchHelper touchHelper = new ItemTouchHelper(new FavoritesItemTouchHelper(
                position -> presenter.removeCity(favoritesAdapter.getCity(position))
        ));

        citiesRecyclerView.setAdapter(favoritesAdapter);
        citiesRecyclerView.setLayoutManager(layoutManager);
        touchHelper.attachToRecyclerView(citiesRecyclerView);

        favoritesAdapter.setListener(this);
    }

    public void initDrawer() {
        MainActivity mainActivity = (MainActivity) getActivity();
        drawerLocker = mainActivity;

        mainActivity
                .getSupportActionBar()
                .setTitle(R.string.main_activity_navigation_favorites);

        drawerLocker
                .selectBurgerButtonNavigation();
    }

    @Override
    public void onListChanged(@NonNull List<City> cities) {
        setEmptyViewEnable(false);
        favoritesAdapter.addAll(cities);
        citiesRecyclerView.smoothScrollToPosition(0);

        unlockBackNavigation();
    }

    @Override
    public void onActiveMissing(@NonNull List<City> cities) {
        setEmptyViewEnable(false);
        favoritesAdapter.addAll(cities);
        citiesRecyclerView.smoothScrollToPosition(0);

        lockBackNavigation();
    }

    @Override
    public void onEmptyList() {
        setEmptyViewEnable(true);
        lockBackNavigation();
    }

    @OnClick(R.id.fragment_favorites_add_city)
    public void onSearchCityClick() {
        showCitySearch();
    }

    @Override
    public void onDialogDismissed() {
    }

    @Override
    public void onClick(City item, int position) {
        presenter.selectCity(item);
    }


    private void showCitySearch() {
        CitySearchFragment.newInstance().show(getChildFragmentManager(), null);
    }

    private void setEmptyViewEnable(boolean isEnabled) {
        emptyView.setVisibility(isEnabled ? View.VISIBLE : View.INVISIBLE);
        citiesRecyclerView.setVisibility(isEnabled ? View.INVISIBLE : View.VISIBLE);
    }

    private void lockBackNavigation() {
        if (drawerLocker == null) return;
        drawerLocker.hideDrawer();
    }

    private void unlockBackNavigation() {
        if (drawerLocker == null) return;
        drawerLocker.selectBurgerButtonNavigation();
    }
}
