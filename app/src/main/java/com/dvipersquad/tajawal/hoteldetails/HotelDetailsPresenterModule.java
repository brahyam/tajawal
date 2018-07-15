package com.dvipersquad.tajawal.hoteldetails;

import com.dvipersquad.tajawal.di.ActivityScoped;
import com.dvipersquad.tajawal.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Brahyam on 15-Jul-18.
 */

@Module
public abstract class HotelDetailsPresenterModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HotelDetailsFragment hotelDetailsFragment();

    @ActivityScoped
    @Binds
    abstract HotelDetailsContract.Presenter detailsPresenter(HotelDetailsPresenter presenter);

}
