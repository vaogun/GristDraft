package com.vaojr.android.gristdraft;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by him on 8/12/15.
 */
public class ItemActivity extends AppCompatActivity {
    String listName;
    TextView tv;
    public static final String KEY_LIST_NAME = "list_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarItem);

        if (toolbar != null) {
            toolbar.showOverflowMenu();
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fC);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fC, fragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_LIST_NAME, listName);
    }

    protected Fragment createFragment() {
        long listId = getIntent().getLongExtra(ItemFragment.EXTRA_LIST_ID_ITEM_FRAGMENT, -1);
        listName = getIntent()
                .getStringExtra(ItemFragment.EXTRA_LIST_NAME_ITEM_FRAGMENT);
        return ItemFragment.newInstance(listId, listName);
    }
}
