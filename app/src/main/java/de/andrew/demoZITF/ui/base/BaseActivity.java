package de.andrew.demoZITF.ui.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import de.andrew.demoZITF.AskTheGuideActivity;
import de.andrew.demoZITF.GetMap;

import de.andrew.demoZITF.MainActivity;
import de.andrew.demoZITF.R;
import de.andrew.demoZITF.Scanner;
import de.andrew.demoZITF.myJSON.MapsActivity;
import de.andrew.demoZITF.sessions.SessionManager;
import de.andrew.demoZITF.ui.AccommodationActivity;
import de.andrew.demoZITF.ui.SettingsActivity;
import de.andrew.demoZITF.ui.ViewSamplesActivity;
import de.andrew.demoZITF.ui.quote.ArticleDetailActivity;
import de.andrew.demoZITF.ui.quote.ArticleDetailFragment;
import de.andrew.demoZITF.ui.quote.ListActivity;
import de.andrew.demoZITF.ui.quote.PlaceListActivity;

import static de.andrew.demoZITF.util.LogUtil.logD;
import static de.andrew.demoZITF.util.LogUtil.makeLogTag;

/**
 * The base class for all Activity classes.
 * This class creates and provides the navigation drawer and toolbar.
 * The navigation logic is handled in {@link BaseActivity#goToNavDrawerItem(int)}
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = makeLogTag(BaseActivity.class);

    protected static final int NAV_DRAWER_ITEM_INVALID = -1;

    private DrawerLayout drawerLayout;
    private Toolbar actionBarToolbar;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    /**
     * Sets up the navigation drawer.
     */
    private void setupNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            // current activity does not have a drawer.
            return;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerSelectListener(navigationView);
            setSelectedItem(navigationView);
        }

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        View header = navigationView.getHeaderView(0);
        TextView txtName = (TextView) header.findViewById(R.id.txtName);
        TextView txtEmail = (TextView) header.findViewById(R.id.txtEmail);
        final ImageView imgProfilePic = (ImageView) header.findViewById(R.id.imageView);
        HashMap<String, String> user = sessionManager.getUserDetails();

        txtName.setText(user.get(SessionManager.KEY_NAME));
        txtEmail.setText(user.get(sessionManager.KEY_EMAIL));

        FacebookSdk.sdkInitialize(getApplicationContext());
        try {
            URL imgUrl = new URL("https://graph.facebook.com/"
                    + user.get(SessionManager.KEY_FACEBOOK_ID) + "/picture?type=large");
            Glide.with(this).load(imgUrl).asBitmap().fitCenter().into(new BitmapImageViewTarget(imgProfilePic) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(BaseActivity.this.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imgProfilePic.setImageDrawable(circularBitmapDrawable);
                }
            });

//            InputStream in = (InputStream) imgUrl.getContent();
//            Bitmap bitmap = BitmapFactory.decodeStream(in);
            //Bitmap bitmap = BitmapFactory.decodeStream(imgUrl      // tried this also
            //.openConnection().getInputStream());
            //Glide.with(BaseActivity.this).load(bitmap).centerCrop().into(imgProfilePic);
//            imgProfilePic.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logD(TAG, "navigation drawer setup finished");
    }

    /**
     * Updated the checked item in the navigation drawer
     * @param navigationView the navigation view
     */
    private void setSelectedItem(NavigationView navigationView) {
        // Which navigation item should be selected?
        int selectedItem = getSelfNavDrawerItem(); // subclass has to override this method
        navigationView.setCheckedItem(selectedItem);
    }

    /**
     * Creates the item click listener.
     * @param navigationView the navigation view
     */
    private void setupDrawerSelectListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        onNavigationItemClicked(menuItem.getItemId());
                        return true;
                    }
                });
    }

    /**
     * Handles the navigation item click.
     * @param itemId the clicked item
     */
    private void onNavigationItemClicked(final int itemId) {
        if(itemId == getSelfNavDrawerItem()) {
            // Already selected
            closeDrawer();
            return;
        }

        goToNavDrawerItem(itemId);
    }

    /**
     * Handles the navigation item click and starts the corresponding activity.
     * @param item the selected navigation item
     */
    private void goToNavDrawerItem(int item) {
        switch (item) {
            case R.id.nav_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.go_to_map:
                startActivity(new Intent(this, GetMap.class));
                finish();
                break;
            case R.id.nav_location:
                startActivity(new Intent(this, PlaceListActivity.class));
                finish();
                break;
            case R.id.nav_myFavourites:
                startActivity(new Intent(this, ListActivity.class));
                finish();
                break;
            case R.id.nav_samples:
                startActivity(new Intent(this, ViewSamplesActivity.class));
                finish();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                break;
            case R.id.nav_scan:
                scan_code();
                finish();
                break;
            case R.id.nav_logout:
                SessionManager manager = new SessionManager(this);
                manager.logoutUser();
                finish();
                break;
        }
    }

    /**
     * Provides the action bar instance.
     * @return the action bar.
     */
    protected ActionBar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);
            }
        }
        return getSupportActionBar();
    }
    public void scan_code(){
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            String scanContent = scanningResult.getContents();
//            String scanFormat = scanningResult.getFormatName();
            Intent detailsIntent = new Intent(this, ArticleDetailActivity.class);
            detailsIntent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, Integer.parseInt(scanContent));
            startActivity(detailsIntent);

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * have to override this method.
     */
    protected int getSelfNavDrawerItem() {
        return NAV_DRAWER_ITEM_INVALID;
    }

    protected void openDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public abstract boolean providesActivityToolbar();

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
