package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.events.OnItemClickListener;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseViewHolder;

/**
 * Created by alexander on 03/08/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<City> cities;
    private OnItemClickListener<City> listener;

    public FavoritesAdapter() {
        cities = new ArrayList<>();
    }

    public void addAll(@NonNull List<City> cities) {
        FavoritesDiffCallback diffCallback = new FavoritesDiffCallback(this.cities, cities);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.cities.clear();
        this.cities.addAll(cities);
        diffResult.dispatchUpdatesTo(this);
    }

    public City getCity(int position) {
        return cities.get(position);
    }

    public void clear() {
        notifyItemRangeRemoved(0, getItemCount());
        cities.clear();
    }

    public void setListener(OnItemClickListener<City> listener) {
        this.listener = listener;
    }

    public class FavoritesViewHolder extends BaseViewHolder {

        @BindView(R.id.row_favorites_card) CardView cityCard;
        @BindView(R.id.row_favorites_city) TextView cityTitle;
        @BindView(R.id.row_favorites_area) TextView cityArea;

        @BindColor(R.color.colorWhite) int white;
        @BindColor(R.color.colorYandexPrimary) int yandex;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
        }

        void setCity(@NonNull City city) {
            cityTitle.setText(city.getTitle());
            cityArea.setText(city.getExtendedTitle());
            cityCard.setCardBackgroundColor(city.isActive() ? yandex : white);
        }

        public boolean isActive() {
            return cities.get(getAdapterPosition()).isActive();
        }

        @OnClick(R.id.row_favorites_clickable)
        public void onClick(View v) {
            if (listener == null) return;
            int position = getAdapterPosition();
            listener.onClick(cities.get(position), position);
        }
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_favorites, parent, false);
        return new FavoritesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        City city = cities.get(position);
        holder.setCity(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
