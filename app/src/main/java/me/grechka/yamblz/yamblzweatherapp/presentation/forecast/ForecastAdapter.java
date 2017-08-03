package me.grechka.yamblz.yamblzweatherapp.presentation.forecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.models.Weather;
import me.grechka.yamblz.yamblzweatherapp.models.weatherTypes.WeatherType;
import me.grechka.yamblz.yamblzweatherapp.presentation.base.BaseViewHolder;
import sasd97.java_blog.xyz.richtextview.RichTextView;

/**
 * Created by alexander on 03/08/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private Context context;
    private String tempUnits;
    private List<Weather> forecasts;
    private Set<WeatherType> weatherTypes;

    public ForecastAdapter(@NonNull String tempUnits,
                           @NonNull Set<WeatherType> weatherTypes) {
        this.tempUnits = tempUnits;
        forecasts = new ArrayList<>();
        this.weatherTypes = weatherTypes;
    }

    public void add(@NonNull Weather weather) {
        forecasts.add(weather);
        notifyItemInserted(getItemCount());
    }

    public void clear() {
        notifyItemRangeRemoved(0, getItemCount());
        forecasts.clear();
    }

    public class ForecastViewHolder extends BaseViewHolder {

        @BindView(R.id.row_forecast_icon) RichTextView forecastIcon;
        @BindView(R.id.row_forecast_temperature) TextView forecastTemperature;
        @BindView(R.id.row_forecast_card) CardView forecastCard;
        @BindView(R.id.row_forecast_delimeter) View forecastDelimeter;

        public ForecastViewHolder(View itemView) {
            super(itemView);
        }

        void setWeather(@NonNull Weather weather,
                        @NonNull WeatherType type) {

            forecastIcon.setText(context.getString(type.getIconRes()));
            forecastIcon.setTextColor(ContextCompat.getColor(context, type.getTextColor()));
            forecastTemperature.setTextColor(ContextCompat.getColor(context, type.getTextColor()));
            forecastTemperature.setText(context.getString(R.string.weather_fragment_temperature,
                    weather.getTemperature(), tempUnits));
            forecastCard.setCardBackgroundColor(ContextCompat.getColor(context, type.getCardColor()));
            forecastDelimeter.setBackgroundColor(ContextCompat.getColor(context, type.getTextColor()));

        }
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_forecast, parent, false);
        return new ForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        Weather weather = forecasts.get(position);

        for(WeatherType type: weatherTypes) {
            if (!type.isApplicable(weather)) continue;
            holder.setWeather(weather, type);
            break;
        }
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }
}
