package de.andrew.demoZITF.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.andrew.demoZITF.R;
import de.andrew.demoZITF.myDataModels.Accommodation;
import de.andrew.demoZITF.myDataModels.DatabaseHandler;
import de.andrew.demoZITF.myDataModels.Place;
import de.andrew.demoZITF.ui.base.BaseActivity;

/**
 * Created by Andrew on 5/8/16.
 */
public class AccommodationActivity extends BaseActivity{
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SimpleItemRecyclerViewAdapter myAdapter;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
//        DatabaseHandler db = new DatabaseHandler(this);
//        db.deleteAll();
        setupToolbar();

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.place_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.place_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });



    }
    void refreshItems() {
        // Load items
        // ...

        DownloadContentTask downloadContentTask =new DownloadContentTask();
        downloadContentTask.execute();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            // This ID represents the Home or Up button. In the case of this
//            //  details, see the Navigation pattern on Android Design:
//            //
//            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
//            //activity, the Up button is shown. Use NavUtils to allow users
//            // to navigate up one level in the application structure. For
//            // more
//            NavUtils.navigateUpFromSameTask(this);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        DatabaseHandler db = new DatabaseHandler(PlaceListActivity.this);
//        List<Place> places = db.getAllPlaces();
        mRecyclerView = recyclerView;
        DownloadContentTask downloadContentTask =new DownloadContentTask();
        downloadContentTask.execute();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
//        ItemTouchHelper.Callback callback = new PlaceTouchHelper(myAdapter);
//        ItemTouchHelper helper = new ItemTouchHelper(callback);
//        helper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_Accommodation;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.GREEN);
                xMark = ContextCompat.getDrawable(AccommodationActivity.this, R.drawable.ic_add_24dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) AccommodationActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                SimpleItemRecyclerViewAdapter testAdapter = (SimpleItemRecyclerViewAdapter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                int swipedPosition = viewHolder.getAdapterPosition();
//                SimpleItemRecyclerViewAdapter adapter = (SimpleItemRecyclerViewAdapter) mRecyclerView.getAdapter();
//                boolean undoOn = adapter.isUndoOn();
//                if (undoOn) {
//                    adapter.pendingRemoval(swipedPosition);
//                } else {
//                    adapter.remove(swipedPosition);
//                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw Green background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * We're gonna setup another ItemDecorator that will draw the red background in the empty space while the items are animating to thier new positions
     * after an item is removed.
     */
    private void setUpAnimationDecoratorHelper() {
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.GREEN);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    /**
     * RecyclerView adapter enabling undo on a swiped away item.
     */

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec

        List<Accommodation> items;
        List<Accommodation> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn = true; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables
        HashMap<Accommodation, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be

        public SimpleItemRecyclerViewAdapter(List<Accommodation> mItems) {
            List<Accommodation> mValues = mItems;
            items = new ArrayList<>();
            itemsPendingRemoval = new ArrayList<>();
            // this should give us a couple of screens worth
            for (int i=0; i< mValues.size(); i++) {
                items.add(mValues.get(i));
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.place_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int mPosition) {
            holder.mItem = items.get(mPosition);
            holder.article_title.setText(items.get(mPosition).getPostTitle());
            holder.article_subtitle.setText(items.get(mPosition).getPostContent());

            if (itemsPendingRemoval.contains(holder.mItem)) {
                // we need to show the "undo" state of the row
                holder.itemView.setBackgroundColor(Color.rgb(25,118,210));
                holder.article_title.setVisibility(View.GONE);
                holder.undoButton.setVisibility(View.VISIBLE);
                holder.undoButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // user wants to undo the removal, let's cancel the pending task
                        Runnable pendingRemovalRunnable = pendingRunnables.get(holder.mItem);
                        pendingRunnables.remove(holder.mItem);
                        if (pendingRemovalRunnable != null)
                            handler.removeCallbacks(pendingRemovalRunnable);
                        itemsPendingRemoval.remove(holder.mItem);
                        // this will rebind the row in "normal" state
                        notifyItemChanged(items.indexOf(holder.mItem));
                    }
                });

            } else {
                // we need to show the "normal" state
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.article_title.setVisibility(View.VISIBLE);
                holder.article_title.setText(holder.mItem.getPostTitle());
                holder.undoButton.setVisibility(View.GONE);
                holder.undoButton.setOnClickListener(null);
            }
            final ImageView mImg = holder.img;
//            Log.e("Place List", "The image url is " + items.get(mPosition).getImgURL());

            Glide.with(AccommodationActivity.this).load(items.get(mPosition).getImgUrl()).asBitmap().fitCenter().into(new BitmapImageViewTarget(mImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(AccommodationActivity.this.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mImg.setImageDrawable(circularBitmapDrawable);
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToDo Replace this with detail activity
                    Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
//                    if (mTwoPane) {
//                        Bundle arguments = new Bundle();
//                        arguments.putInt(PlaceDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
//                        PlaceDetailFragment fragment = new PlaceDetailFragment();
//                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.place_detail_container, fragment)
//                                .commit();
//                    } else {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, PlaceDetailActivity.class);
//                        intent.putExtra(PlaceDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
//
//                        context.startActivity(intent);
//                    }
                }
            });
        }

        /**
         * Utility method to add some rows for testing purposes. You can add rows from the toolbar menu.
         */
//        public void addItems(int howMany) {
//            if (howMany > 0) {
//                for (int i = lastInsertedIndex + 1; i <= lastInsertedIndex + howMany; i++) {
//                    items.add("Item " + i);
//                    notifyItemInserted(items.size() - 1);
//                }
//                lastInsertedIndex = lastInsertedIndex + howMany;
//            }
//        }

        public void setUndoOn(boolean undoOn) {
            this.undoOn = undoOn;
        }

        public boolean isUndoOn() {
            return undoOn;
        }

        public void pendingRemoval(int position) {
            final Accommodation item = items.get(position);
            if (!itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.add(item);
                // this will redraw row in "undo" state
                notifyItemChanged(position);
                // let's create, store and post a runnable to remove the item
                Runnable pendingRemovalRunnable = new Runnable() {
                    @Override
                    public void run() {
                        remove(items.indexOf(item));
                    }
                };
                handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
                pendingRunnables.put(item, pendingRemovalRunnable);
            }
        }

        public void remove(int position) {
            Accommodation item = items.get(position);
            DatabaseHandler db = new DatabaseHandler(AccommodationActivity.this);
            if(db.addAccommodation(item)==true) {
                if (itemsPendingRemoval.contains(item)) {
                    itemsPendingRemoval.remove(item);
                }
                if (items.contains(item)) {
                    items.remove(position);
                    notifyItemRemoved(position);
                }
            }else {
                Toast.makeText(AccommodationActivity.this, "Record already in the database", Toast.LENGTH_SHORT).show();
            }
        }

        public boolean isPendingRemoval(int position) {
            Accommodation item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }


        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView article_title;
            public final TextView article_subtitle;
            public final ImageView img;
            public final Button undoButton;
            public Accommodation mItem;
//            final Place item = (Place) getItem(position);
//            ((TextView) convertView.findViewById(R.id.article_title)).setText(item.getPlaceName());
//            ((TextView) convertView.findViewById(R.id.article_subtitle)).setText(item.getDescription());
//            final ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);

            public ViewHolder(View view) {
                super(view);
                mView = view;
                article_title = (TextView) view.findViewById(R.id.article_title);
                article_subtitle = (TextView) view.findViewById(R.id.article_subtitle);
                img = (ImageView) view.findViewById(R.id.thumbnail);
                undoButton= (Button) view.findViewById(R.id.undo_button);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + article_subtitle.getText() + "'";
            }
        }
    }
    /**
     * ViewHolder capable of presenting two states: "normal" and "undo" state.
     */

    private ArrayList<Accommodation>getDataFromJson(String accommodationJsonStr)
            throws JSONException {
        Log.e(LOG_TAG, "SUccessfully called Get data from JSON");
        // These are the names of the JSON objects that need to be extracted.
        final String ACC_ID = "ID";
        final String ACC_TITLE = "post_title";
        final String ACC_DESCRIPTION = "post_content";
        final String ACC_MIN_DAYS_STAY = "accommodation_min_days_stay";
        final String ACC_MAX_CHILD_COUNT = "accommodation_max_child_count";
        final String ACC_MAX_COUNT = "accommodation_max_count";
        final String ACC_IS_PRICE_PER_PERSON = "accommodation_is_price_per_person";
        final String ACC_STAR_COUNT = "accommodation_star_count";
        final String ACC_LOCATION_ID = "accommodation_location_post_id";
        final String ACC_ACTIVITIES = "accommodation_activities";
        final String ACC_IMG = "guid";

        JSONObject jsonResult = new JSONObject(accommodationJsonStr);
        JSONArray accommodationArray = jsonResult.getJSONArray("data");
        if (accommodationArray!=null){
            Log.e(LOG_TAG, accommodationArray.getString(1).toString());
        }else {
            Log.e(LOG_TAG, "Location Array is empty");
        }

        ArrayList<Accommodation> mResults = new ArrayList<Accommodation>();
        Accommodation[] resultStrs = new Accommodation[accommodationArray.length()];
            int m = accommodationArray.length();
//            Log.e(LOG_TAG, "The length of Result Array is: "+accommodationArray.length());
        for(int i = 0; i < m; i++) {
            // Get the JSON object representing the Location
            JSONObject singleAccommodation = accommodationArray.getJSONObject(i);
            Accommodation accommodation = new Accommodation();
            accommodation.setID(singleAccommodation.getString(ACC_ID));
            accommodation.setPostTitle(singleAccommodation.getString(ACC_TITLE));
            accommodation.setPostContent(singleAccommodation.getString(ACC_DESCRIPTION));
            accommodation.setAccommodationStarCount(singleAccommodation.getString(ACC_STAR_COUNT));
            accommodation.setAccommodationLocationPostId(singleAccommodation.getString(ACC_LOCATION_ID));
            accommodation.setAccommodationMaxChildCount(singleAccommodation.getString(ACC_MAX_CHILD_COUNT));
            accommodation.setAccommodationMaxCount(singleAccommodation.getString(ACC_MAX_COUNT));
            accommodation.setAccommodationMinDaysStay(ACC_MIN_DAYS_STAY);
            accommodation.setAccommodationActivities(singleAccommodation.getString(ACC_ACTIVITIES));
            accommodation.setAccommodationIsPricePerPerson(singleAccommodation.getString(ACC_IS_PRICE_PER_PERSON));
            accommodation.setImgUrl(singleAccommodation.getString(ACC_IMG.replaceAll("\\\\","")));
            mResults.add(accommodation);


            Log.e(LOG_TAG, "Title " + 1 + " = " + accommodation.getImgUrl());

        }
        Log.e(LOG_TAG, "Place entry 1 : " + mResults.get(1).getPostTitle().toString());
        return mResults;
    }
    String LOG_TAG = "ASYNCTASK";

    private class DownloadContentTask extends AsyncTask<Void, Void, ArrayList<Accommodation>> {

        protected ArrayList<Accommodation> doInBackground(Void... voidsv) {

            //android.os.Debug.waitForDebugger();
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String mLocationsJsonStr = null;
            Log.e(LOG_TAG, "Starting background thread");
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at LOC's forecast API page, at
                // http://openweathermap.org/API#forecast
                String baseUrl = "http://10.0.2.2/mydemo/accommodation_api.php";
                URL url = new URL(baseUrl);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                mLocationsJsonStr = buffer.toString();
                Log.e(LOG_TAG, mLocationsJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                Log.e(LOG_TAG, "Calling the getData fromJSON method");
                return getDataFromJson(mLocationsJsonStr);
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(ArrayList<Accommodation> result) {
            for (Accommodation a:result){
                Log.e(LOG_TAG,a.getPostTitle());
            }
            myAdapter = new SimpleItemRecyclerViewAdapter(result);
            mRecyclerView.setAdapter(myAdapter);
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
            DatabaseHandler db = new DatabaseHandler(AccommodationActivity.this);
            for (Accommodation item:result) {
                if (db.addAccommodation(item) == true) {

                } else {
//                    Toast.makeText(AccommodationActivity.this, "Record already in the database", Toast.LENGTH_SHORT).show();
                }
            }
            Log.e(LOG_TAG, " Finished Executing ");
        }
    }

}
