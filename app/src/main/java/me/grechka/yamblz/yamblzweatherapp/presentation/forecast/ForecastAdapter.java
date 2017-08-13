package me.grechka.yamblz.yamblzweatherapp.presentation.forecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    private String tempUnits = "°";
    private List<Weather> forecasts;
    private Set<WeatherType> weatherTypes;

    public ForecastAdapter(@NonNull Set<WeatherType> weatherTypes) {
        forecasts = new ArrayList<>();
        this.weatherTypes = weatherTypes;
    }

    public void setTempUnits(@NonNull String tempUnits) {
        this.tempUnits = tempUnits;
    }

    public void addAll(@NonNull List<Weather> forecast) {
        ForecastDiffCallback diffCallback = new ForecastDiffCallback(this.forecasts, forecast);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.forecasts.clear();
        this.forecasts.addAll(forecast);
        diffResult.dispatchUpdatesTo(this);
    }

    public class ForecastViewHolder extends BaseViewHolder {

        @BindView(R.id.row_forecast_date) TextView forecastDate;
        @BindView(R.id.row_forecast_card) CardView forecastCard;
        @BindView(R.id.row_forecast_icon) RichTextView forecastIcon;
        @BindView(R.id.row_forecast_delimeter) View forecastDelimiter;
        @BindView(R.id.row_forecast_temperature) TextView forecastTemperature;

        public ForecastViewHolder(View itemView) {
            super(itemView);
        }

        void setWeather(@NonNull Weather weather,
                        @NonNull WeatherType type) {
            forecastIcon.setText(context.getString(type.getIconRes()));
            forecastTemperature.setText(context.getString(R.string.weather_fragment_temperature,
                    weather.getTemperature(), tempUnits));
            forecastIcon.setTextColor(ContextCompat.getColor(context, type.getTextColor()));
            forecastTemperature.setTextColor(ContextCompat.getColor(context, type.getTextColor()));

            forecastCard.setCardBackgroundColor(ContextCompat.getColor(context, type.getCardColor()));
            forecastDelimiter.setBackgroundColor(ContextCompat.getColor(context, type.getTextColor()));
            forecastDate.setTextColor(ContextCompat.getColor(context, type.getTextColor()));

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM", Locale.US);
            String format = formatter.format(new Date(weather.getUpdateTime()));
            forecastDate.setText(format);
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
