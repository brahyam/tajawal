package com.dvipersquad.tajawal.di;

import com.dvipersquad.tajawal.fullscreenphoto.FullScreenPhotoActivity;
import com.dvipersquad.tajawal.fullscreenphoto.FullScreenPhotoPresenterModule;
import com.dvipersquad.tajawal.hoteldetails.HotelDetailsActivity;
import com.dvipersquad.tajawal.hoteldetails.HotelDetailsPresenterModule;
import com.dvipersquad.tajawal.hotels.HotelsActivity;
import com.dvipersquad.tajawal.hotels.HotelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = HotelsModule.class)
    abstract HotelsActivity hotelsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = HotelDetailsPresenterModule.class)
    abstract HotelDetailsActivity hotelDetailsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FullScreenPhotoPresenterModule.class)
    abstract FullScreenPhotoActivity fullScreenPhotoActivity();
}
