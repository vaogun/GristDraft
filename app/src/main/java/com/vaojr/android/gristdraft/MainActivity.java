package com.vaojr.android.gristdraft;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;


public class MainActivity extends AppCompatActivity {

    SectionPagerAdapter mSectionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);


        if (toolbar != null) {
            toolbar.showOverflowMenu();
            toolbar.setNavigationIcon(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setSelectedIndicatorColors(-1);
        mSlidingTabLayout.setDividerColors(-1);
        viewPager.setAdapter(mSectionPagerAdapter);
        mSlidingTabLayout.setViewPager(viewPager);

        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ListingFragment();
                case 1:
                    return new RecipeFragment();
                case 2:
                    return new RecipeFragment();
                case 3:
                    return new RecipeFragment();
                case 4:
                    return new RecipeFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Lists";
                case 1:
                    return "Pantry";
                case 2:
                    return "Recipes";
                case 3:
                    return "Shop Mode";
                case 4:
                    return "Sync";
                default:
                    return null;
            }
        }
    }
}
