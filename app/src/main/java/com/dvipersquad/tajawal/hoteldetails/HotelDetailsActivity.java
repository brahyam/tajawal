package com.dvipersquad.tajawal.hoteldetails;

import android.os.Bundle;

import com.dvipersquad.tajawal.R;
import com.dvipersquad.tajawal.utils.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Displays hotel details screen
 */
public class HotelDetailsActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_HOTEL_ID = "HOTEL_ID";

    @Inject
    HotelDetailsFragment injectedHotelDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoteldetails_act);

        HotelDetailsFragment hotelDetailsFragment = (HotelDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (hotelDetailsFragment == null) {
            hotelDetailsFragment = this.injectedHotelDetailsFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    hotelDetailsFragment, R.id.contentFrame);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
