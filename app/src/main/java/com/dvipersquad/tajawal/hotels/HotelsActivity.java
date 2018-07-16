package com.dvipersquad.tajawal.hotels;

import android.os.Bundle;

import com.dvipersquad.tajawal.R;
import com.dvipersquad.tajawal.utils.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;


public class HotelsActivity extends DaggerAppCompatActivity {

    @Inject
    HotelsPresenter hotelsPresenter;

    @Inject
    Lazy<HotelsFragment> hotelsFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotels_act);

        HotelsFragment hotelsFragment = (HotelsFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (hotelsFragment == null) {
            hotelsFragment = hotelsFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    hotelsFragment,
                    R.id.contentFrame);
        }
    }
}
