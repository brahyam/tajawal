package com.dvipersquad.tajawal.fullscreenphoto;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import javax.inject.Inject;

public class FullScreenPhotoPresenter implements FullScreenPhotoContract.Presenter {

    @Nullable
    private FullScreenPhotoContract.View fullScreenPhotoView;

    @Nullable
    private String photoUrl;

    @Inject
    public FullScreenPhotoPresenter(@Nullable String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private void loadPhoto() {
        if (TextUtils.isEmpty(photoUrl)) {
            fullScreenPhotoView.showMissingPhoto();
            return;
        }

        fullScreenPhotoView.showPhoto(photoUrl);
    }

    @Override
    public void takeView(FullScreenPhotoContract.View view) {
        fullScreenPhotoView = view;
        loadPhoto();
    }

    @Override
    public void dropView() {
        fullScreenPhotoView = null;
    }

    @Override
    public void closePhoto() {
        fullScreenPhotoView.closePhotoUI();
    }
}
