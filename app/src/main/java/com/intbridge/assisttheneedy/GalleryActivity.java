package com.intbridge.assisttheneedy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Derek on 9/10/2015.
 */
@EActivity(R.layout.activity_gallery)
public class GalleryActivity extends FragmentActivity {
    @ViewById
    ViewPager gallery_viewpager;
    @ViewById
    CircleIndicator gallery_indicator;

    @ViewById
    TextView enterform;

    @AfterViews
    public void afterViews(){
        // DEFAULT
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        gallery_viewpager.setAdapter(pagerAdapter);
        gallery_indicator.setViewPager(gallery_viewpager);
        delayTheFirstImageAndShowFollowing();
    }

    @Click
    public void enterformClicked(){
        Intent i = new Intent(GalleryActivity.this, MainActivity_.class);
        startActivity(i);
        finish();
    }

    @Background
    public void delayTheFirstImageAndShowFollowing(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(4000);
                    pageSwitcher(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private Timer timer;
    private int page = 0;

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {
                    page = gallery_viewpager.getCurrentItem();
                    page++;
                    if (page > 8) { // In my case the number of pages are 5
                        page = 0;
                        gallery_viewpager.setCurrentItem(page);
                    } else {
                        gallery_viewpager.setCurrentItem(page);
                    }
                }
            });

        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private int pagerCount = 9;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int i) {
            return ImageFragment.newInstance(i);
        }

        @Override public int getCount() {
            return pagerCount;
        }
    }


}

