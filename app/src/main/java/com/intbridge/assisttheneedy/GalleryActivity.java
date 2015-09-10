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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

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
    }

    @Click
    public void enterformClicked(){
        Intent i = new Intent(GalleryActivity.this, MainActivity_.class);
        startActivity(i);
        finish();
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

