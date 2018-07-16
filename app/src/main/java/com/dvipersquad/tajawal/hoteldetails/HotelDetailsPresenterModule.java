package com.dvipersquad.tajawal.hoteldetails;

import com.dvipersquad.tajawal.di.ActivityScoped;
import com.dvipersquad.tajawal.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HotelDetailsPresenterModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HotelDetailsFragment hotelDetailsFragment();

    @ActivityScoped
    @Binds
    abstract HotelDetailsContract.Presenter detailsPresenter(HotelDetailsPresenter presenter);

    @Provides
    @ActivityScoped
    static Integer provideHotelId(HotelDetailsActivity activity) {
        return activity.getIntent().getIntExtra(HotelDetailsActivity.EXTRA_HOTEL_ID, -1);
    }

}
