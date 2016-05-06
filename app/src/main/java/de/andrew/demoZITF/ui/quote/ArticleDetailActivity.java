package de.andrew.demoZITF.ui.quote;

import android.os.Bundle;
import android.util.Log;

import de.andrew.demoZITF.R;
import de.andrew.demoZITF.sessions.SessionManager;
import de.andrew.demoZITF.ui.base.BaseActivity;

/**
 * Simple wrapper for {@link ArticleDetailFragment}
 * This wrapper is only used in single pan mode (= on smartphones)
 * Created by Andreas Schrade on 14.12.2015.
 */
public class ArticleDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Show the Up button in the action bar.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        int theID = getIntent().getExtras().getInt(ArticleDetailFragment.ARG_ITEM_ID);
        ArticleDetailFragment fragment =  ArticleDetailFragment.newInstance(theID);
        getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
