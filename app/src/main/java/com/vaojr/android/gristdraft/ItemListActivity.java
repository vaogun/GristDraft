package com.vaojr.android.gristdraft;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by him on 8/3/15.
 */
public class ItemListActivity extends AppCompatActivity {
    String listName;
    TextView tv;
    public static final String KEY_LIST_NAME = "list_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarItemName);
        tv = (TextView) findViewById(R.id.abcd);

        if (toolbar != null) {
            toolbar.showOverflowMenu();
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainerItemList);


        if (savedInstanceState != null & fragment != null) {
            listName = savedInstanceState.getString(KEY_LIST_NAME);
            tv.setText(listName);
        }


        if (fragment == null) {
            fragment = createFragment();

            tv.setText(listName);
            manager.beginTransaction()
                    .add(R.id.fragmentContainerItemList, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_LIST_NAME, listName);
    }


    protected Fragment createFragment() {
            long listId = getIntent().getLongExtra(ItemListFragment.EXTRA_LIST_ID, -1);
            listName = getIntent()
                    .getStringExtra(ItemListFragment.EXTRA_LIST_NAME);

            return ItemListFragment.newInstance(listId, listName);
    }

}
