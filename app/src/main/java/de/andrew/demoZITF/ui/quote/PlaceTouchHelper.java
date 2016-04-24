package de.andrew.demoZITF.ui.quote;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Andrew on 4/21/16.
 */
public class PlaceTouchHelper extends ItemTouchHelper.SimpleCallback {
    private PlaceListActivity.SimpleItemRecyclerViewAdapter mMovieAdapter;

    public PlaceTouchHelper(PlaceListActivity.SimpleItemRecyclerViewAdapter movieAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mMovieAdapter = movieAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //TODO: Not implemented here
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Remove item
    }
}