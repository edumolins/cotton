package com.cotton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cotton.fragments.VenueEventsFragment;
import com.cotton.fragments.VenueGalleryFragment;
import com.cotton.fragments.VenueLocationFragment;
import com.cotton.fragments.VenueMenuFragment;
import com.cotton.fragments.VenueReservationsFragment;
import com.cotton.fragments.VenueWelcomeFragment;
import com.cotton.ui.AvenirTabLayout;

import java.util.ArrayList;
import java.util.List;

public class VenueActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AvenirTabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (AvenirTabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setVisibility(View.GONE);
        //actionBar.setDisplayShowHomeEnabled(false);
        /*viewPager.setOnTouchListener(new View.OnTouchListener() {

            int dragthreshold = 30;
            int downX;
            int downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int distanceX = Math.abs((int) event.getRawX() - downX);
                        int distanceY = Math.abs((int) event.getRawY() - downY);

                        if (distanceY > distanceX && distanceY > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                            mScrollView.getParent().requestDisallowInterceptTouchEvent(true);
                        } else if (distanceX > distanceY && distanceX > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                            mScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VenueWelcomeFragment(), getString(R.string.welcome));
        adapter.addFragment(new VenueGalleryFragment(), getString(R.string.gallery));
        adapter.addFragment(new VenueReservationsFragment(), getString(R.string.reservations));
        adapter.addFragment(new VenueLocationFragment(), getString(R.string.location));
        adapter.addFragment(new VenueMenuFragment(), getString(R.string.menu));
        adapter.addFragment(new VenueEventsFragment(), getString(R.string.events));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
