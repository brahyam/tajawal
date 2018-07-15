package com.dvipersquad.tajawal.di;

import com.dvipersquad.tajawal.hoteldetails.HotelDetailsActivity;
import com.dvipersquad.tajawal.hoteldetails.HotelDetailsPresenterModule;
import com.dvipersquad.tajawal.hotels.HotelsActivity;
import com.dvipersquad.tajawal.hotels.HotelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Brahyam on 15-Jul-18.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = HotelsModule.class)
    abstract HotelsActivity hotelsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = HotelDetailsPresenterModule.class)
    abstract HotelDetailsActivity hotelDetailsActivity();
}
