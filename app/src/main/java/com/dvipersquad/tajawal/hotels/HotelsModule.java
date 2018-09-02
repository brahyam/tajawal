package com.dvipersquad.tajawal.hotels;

import com.dvipersquad.tajawal.di.ActivityScoped;
import com.dvipersquad.tajawal.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HotelsModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract HotelsFragment hotelsFragment();

    @ActivityScoped
    @Binds
    abstract HotelsContract.Presenter hotelPresenter(HotelsPresenter presenter);
}
