package com.dvipersquad.tajawal.fullscreenphoto;

import com.dvipersquad.tajawal.di.ActivityScoped;
import com.dvipersquad.tajawal.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FullScreenPhotoPresenterModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FullScreenPhotoFragment fullScreenPhotoFragment();

    @ActivityScoped
    @Binds
    abstract FullScreenPhotoContract.Presenter fullScreenPhotoPresenter(FullScreenPhotoPresenter presenter);

    @Provides
    @ActivityScoped
    static String providePhotoUrl(FullScreenPhotoActivity activity) {
        return activity.getIntent().getStringExtra(FullScreenPhotoActivity.EXTRA_PHOTO_URL);
    }

}
