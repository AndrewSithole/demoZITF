package de.andrew.demoZITF;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;

import butterknife.ButterKnife;
import de.andrew.demoZITF.sessions.SessionManager;
import de.andrew.demoZITF.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        Button addPlace = (Button)findViewById(R.id.addPlace);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createRealmInstance();
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
//    public void createRealmInstance(){
//        RealmList<PlaceServices> serviceList = new RealmList<PlaceServices>();
//        PlaceServices service1 = new PlaceServices("Innovating",400);
//        PlaceServices service2 = new PlaceServices("Engineering", 600);
//        serviceList.add(service1);
//        serviceList.add(service2);
//
//
//        Realm myRealm = Realm.getInstance(this);
//        myRealm.beginTransaction();
//        Place sBlock = myRealm.createObject(Place.class);
//        sBlock.setPlaceName("S-Block");
//        sBlock.setPlaceType("Class Block");
//        sBlock.setPlaceServices(serviceList);
//        myRealm.commitTransaction();
//    }
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
        return R.id.nav_samples;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
