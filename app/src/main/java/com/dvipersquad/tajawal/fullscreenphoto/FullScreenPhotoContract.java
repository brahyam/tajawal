package com.dvipersquad.tajawal.fullscreenphoto;

import com.dvipersquad.tajawal.BasePresenter;
import com.dvipersquad.tajawal.BaseView;

/**
 * Specifies the contract between the view and the presenter
 */
public class FullScreenPhotoContract {

    interface View extends BaseView<Presenter> {

        void showPhoto(String url);

        void showMissingPhoto();

        void closePhotoUI();

        boolean isActive();
    }

    interface Presenter extends BasePresenter<View> {

        void takeView(FullScreenPhotoContract.View view);

        void dropView();

        void closePhoto();

    }
}
