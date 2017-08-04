package me.grechka.yamblz.yamblzweatherapp.presentation.favorites;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import me.grechka.yamblz.yamblzweatherapp.events.Action;
import me.grechka.yamblz.yamblzweatherapp.models.City;

/**
 * Created by alexander on 04/08/2017.
 */

public class FavoritesItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private Action<Integer> action;

    public FavoritesItemTouchHelper(Action<Integer> action) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.action = action;
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof FavoritesAdapter.FavoritesViewHolder &&
                ((FavoritesAdapter.FavoritesViewHolder) viewHolder).isActive()) return 0;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        action.execute(viewHolder.getAdapterPosition());
    }
}
