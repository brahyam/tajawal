package com.dvipersquad.tajawal;

import com.dvipersquad.tajawal.data.source.HotelsRepository;
import com.dvipersquad.tajawal.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class TajawalApp extends DaggerApplication {

    @Inject
    HotelsRepository hotelsRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
