package de.andrew.demoZITF;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.andrew.demoZITF.myDataModels.DatabaseHandler;
import de.andrew.demoZITF.myDataModels.Place;
import de.andrew.demoZITF.sessions.SessionManager;
import de.andrew.demoZITF.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();

        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);

        Button addPlace = (Button)findViewById(R.id.addPlace);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place place =new Place();
                DatabaseHandler handler = new DatabaseHandler(MainActivity.this);

                List<Place> places = handler.getAllPlaces();

                place = places.get(1);

                TextView textView1 = (TextView)findViewById(R.id.txtPlaceName);
                TextView txtDesc = (TextView) findViewById(R.id.txtDescriptoin);

                textView1.setText(place.getPlaceName());
                txtDesc.setText(place.getDescription());
            }
        });

        Button logout = (Button)findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager manager = new SessionManager(MainActivity.this);
                manager.logoutUser();
            }
        });
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
