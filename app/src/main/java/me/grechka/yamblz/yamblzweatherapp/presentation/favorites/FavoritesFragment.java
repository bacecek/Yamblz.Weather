package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

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
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.citySearch.CitySearchFragment;
import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;

/**
 * Created by alexander on 03/08/2017.
 */

public class FavoritesFragment extends BaseFragment
        implements FavoritesView,
        OnItemClickListener<City>,
        OnDismissDialogListener {

    private FavoritesAdapter favoritesAdapter;
    private RecyclerView.LayoutManager layoutManager;

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
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);
        favoritesAdapter = new FavoritesAdapter();


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getContext());
        } else {
            layoutManager = new GridLayoutManager(getContext(), 2);
        }

        ItemTouchHelper touchHelper = new ItemTouchHelper(new FavoritesItemTouchHelper(
                position -> presenter.removeCity(favoritesAdapter.getCity(position))
        ));

        citiesRecyclerView.setAdapter(favoritesAdapter);
        citiesRecyclerView.setLayoutManager(layoutManager);
        touchHelper.attachToRecyclerView(citiesRecyclerView);

        favoritesAdapter.setListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity
                .getSupportActionBar()
                .setTitle(R.string.main_activity_navigation_favorites);

        mainActivity
                .disableDrawer();
    }

    @Override
    public void citiesListChanged(@NonNull List<City> cities) {
        favoritesAdapter.addAll(cities);
        for (City city: cities) Log.d("CITIES", city.toString());
    }

    @OnClick(R.id.fragment_favorites_add_city)
    public void onSearchCityClick() {
        showCitySearch();
    }

    @Override
    public void onDialogDismissed() {
        presenter.updateCities();
    }

    @Override
    public void onClick(City item, int position) {
        presenter.selectCity(item);
    }

    @Override
    public void clearList() {
        favoritesAdapter.clear();
    }

    private void showCitySearch() {
        CitySearchFragment.newInstance().show(getChildFragmentManager(), null);
    }
}
