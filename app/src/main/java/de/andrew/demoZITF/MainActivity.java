package de.andrew.demoZITF;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.andrew.demoZITF.myDataModels.DatabaseHandler;
import de.andrew.demoZITF.myDataModels.Place;
import de.andrew.demoZITF.sessions.SessionManager;
import de.andrew.demoZITF.ui.AccommodationActivity;
import de.andrew.demoZITF.ui.base.BaseActivity;
import de.andrew.demoZITF.ui.quote.PlaceListActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        ImageView first = (ImageView)findViewById(R.id.img1);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AskTheGuideActivity.class));

            }
        });
        ImageView second = (ImageView)findViewById(R.id.img2);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlaceListActivity.class));

            }
        });
        ImageView third = (ImageView)findViewById(R.id.img3);
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccommodationActivity.class));

            }
        });
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        Glide.with(this).load(R.drawable.ask_guide).centerCrop().into(first);
        Glide.with(this).load(R.drawable.places).centerCrop().into(second);
        Glide.with(this).load(R.drawable.accommodation).centerCrop().into((third));

    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
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

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_home;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
