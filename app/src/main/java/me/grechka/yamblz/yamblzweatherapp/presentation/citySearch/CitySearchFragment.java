package me.grechka.yamblz.yamblzweatherapp.presentation.citySearch;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.WeatherApp;
import me.grechka.yamblz.yamblzweatherapp.events.OnDismissDialogListener;
import me.grechka.yamblz.yamblzweatherapp.events.OnItemClickListener;
import me.grechka.yamblz.yamblzweatherapp.models.City;

/**
 * Created by alexander on 22/07/2017.
 */

public class CitySearchFragment extends MvpAppCompatDialogFragment
        implements CitySearchView, OnItemClickListener<City> {

    @Inject @InjectPresenter CitySearchPresenter presenter;

    @BindView(R.id.fragment_city_search_edittext) EditText searchEditText;
    @BindView(R.id.fragment_city_search_progress_bar) ProgressBar loadingProgressBar;
    @BindView(R.id.fragment_city_search_recycler_view) RecyclerView suggestRecyclerView;

    private OnDismissDialogListener listener;
    private CitySearchAdapter adapter = new CitySearchAdapter();
    private LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

    @ProvidePresenter
    CitySearchPresenter providePresenter() {
        return presenter;
    }

    public static CitySearchFragment newInstance() {
        return new CitySearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WeatherApp
                .get(context)
                .getAppComponent()
                .addMainComponent()
                .inject(this);

        listener = (OnDismissDialogListener) getParentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SearchCityDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter.setOnItemClickListener(this);
        suggestRecyclerView.setLayoutManager(layoutManager);
        suggestRecyclerView.setAdapter(adapter);

        presenter.setObservable(RxTextView
                .textChanges(searchEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(text -> text.length() > 0)
                .observeOn(AndroidSchedulers.mainThread()));
    }

    @Override
    public void onClick(City item, int position) {
        presenter.fetchCity(item);
    }

    @Override
    public void addSuggestion(City suggestion) {
        adapter.add(suggestion);
    }

    @Override
    public void clearSuggestions() {
        adapter.clear();
    }

    @Override
    public void showLoading() {
        suggestRecyclerView.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        suggestRecyclerView.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onDialogDismissed();
    }
}
