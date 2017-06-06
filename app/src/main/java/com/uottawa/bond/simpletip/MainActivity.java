package com.uottawa.bond.simpletip;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Tab2Home.OnDataSetListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    Tab1Settings tab1;
    Tab2Home tab2;
    Tab3Summary tab3;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager, true);

        mViewPager.setCurrentItem(1);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            SharedPreferences sp = getApplicationContext().getSharedPreferences("defaultValues", getApplicationContext().MODE_PRIVATE);
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2){
                    Tab2Home homefrag = (Tab2Home) mSectionsPagerAdapter.findItem(1);
                    if (homefrag.noData()==0) {
                        mViewPager.setCurrentItem(1);
                        Toast.makeText(getApplicationContext(), "Insufficient data. Missing \"Bill Amount\"",Toast.LENGTH_SHORT).show();
                    }
                    else if (homefrag.noData()==1){
                        mViewPager.setCurrentItem(1);
                        Toast.makeText(getApplicationContext(), "Insufficient data. Missing \"Tip\"",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        homefrag.transferInfo();
                    }
                }
                else if (position == 1 && sp.getBoolean("changed", false)){
                    Tab2Home homefrag = (Tab2Home) mSectionsPagerAdapter.findItem(1);
                    homefrag.setDefaults(sp.getInt("tip", 0), sp.getInt("currency", 0));
                    SharedPreferences.Editor e = sp.edit();
                    e.putBoolean("changed", false);
                    e.apply();
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    Tab2Home homefrag = (Tab2Home) mSectionsPagerAdapter.findItem(1);
                    if (homefrag.noData()==0) {
                        mViewPager.setCurrentItem(1);
                        Toast.makeText(getApplicationContext(), "Insufficient data. Missing \"Bill Amount\"",Toast.LENGTH_SHORT).show();
                    }
                    else if (homefrag.noData()==1){
                        mViewPager.setCurrentItem(1);
                        Toast.makeText(getApplicationContext(), "Insufficient data. Missing \"Tip\"",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        homefrag.transferInfo();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void setData(double bill, double tip, int ppl) {
        tab3.updateInfo(bill, tip, ppl);
    }

    @Override
    public void calculate() {
        mViewPager.setCurrentItem(2);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    tab1 = new Tab1Settings();
                    return tab1;
                case 1:
                    tab2 = new Tab2Home();
                    return tab2;
                case 2:
                    tab3 = new Tab3Summary();
                    return tab3;
                default:
                    return null;
            }
        }

        public Fragment findItem(int position) {
            switch (position){
                case 0:
                    return tab1;
                case 1:
                    return tab2;
                case 2:
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SETTINGS";
                case 1:
                    return "HOME";
                case 2:
                    return "SUMMARY";
            }
            return null;
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
