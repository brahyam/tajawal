package com.dvipersquad.tajawal.hotels;

import com.dvipersquad.tajawal.di.ActivityScoped;
import com.dvipersquad.tajawal.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Passes View dependency to HotelsPresenter
 */
@Module
public abstract class HotelModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract HotelsFragment hotelsFragment();

    @ActivityScoped
    @Binds
    abstract HotelsContract.Presenter hotelPresenter(HotelsPresenter presenter);
}
