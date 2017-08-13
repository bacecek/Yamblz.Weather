package me.grechka.yamblz.yamblzweatherapp.presentation.citySearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.events.OnItemClickListener;
import me.grechka.yamblz.yamblzweatherapp.models.City;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseViewHolder;
import sasd97.java_blog.xyz.circleview.CircleView;

/**
 * Created by alexander on 23/07/2017.
 */

public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.CitySearchViewHolder> {

    private OnItemClickListener<City> listener;
    private List<City> cities = new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener<City> listener) {
        this.listener = listener;
    }

    class CitySearchViewHolder extends BaseViewHolder
        implements View.OnClickListener {

        @BindView(R.id.row_city_title) TextView cityTitle;
        @BindView(R.id.row_city_description) TextView cityArea;
        @BindView(R.id.row_city_alias) CircleView cityLogoHolder;
        @BindView(R.id.row_city_clickable_area) View clickRegionView;

        CitySearchViewHolder(View itemView) {
            super(itemView);
            clickRegionView.setOnClickListener(this);
        }

        public void setCity(@NonNull City city) {
            cityTitle.setText(city.getTitle());
            cityArea.setText(city.getExtendedTitle());
            cityLogoHolder.setText(city.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (listener == null) return;
            int position = getAdapterPosition();
            listener.onClick(cities.get(position), position);
        }
    }

    @Override
    public CitySearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_city, parent, false);
        return new CitySearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CitySearchViewHolder holder, int position) {
        City city = cities.get(position);
        holder.setCity(city);
    }

    public void add(@NonNull City item) {
        this.cities.add(item);
        notifyItemInserted(getItemCount());
    }

    public void clear() {
        int oldLength = getItemCount();
        this.cities.clear();
        notifyItemRangeRemoved(0, oldLength);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
