package com.dvipersquad.tajawal.fullscreenphoto;

import android.os.Bundle;

import com.dvipersquad.tajawal.R;
import com.dvipersquad.tajawal.utils.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Displays full screen photos
 */
public class FullScreenPhotoActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_PHOTO_URL = "PHOTO_URL";

    @Inject
    FullScreenPhotoFragment injectedFullScreenPhotoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreenphoto_act);

        FullScreenPhotoFragment fullScreenPhotoFragment =
                (FullScreenPhotoFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.contentFrame);

        if (fullScreenPhotoFragment == null) {
            fullScreenPhotoFragment = this.injectedFullScreenPhotoFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fullScreenPhotoFragment, R.id.contentFrame);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
