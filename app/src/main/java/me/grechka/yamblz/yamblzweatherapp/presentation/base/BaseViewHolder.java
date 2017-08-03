package me.grechka.yamblz.yamblzweatherapp.presentation.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by alexander on 03/08/2017.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        onViewsCreated();
    }

    protected void onViewsCreated() {
    }
}
