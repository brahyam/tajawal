package com.dvipersquad.tajawal.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Brahyam on 15-Jul-18.
 */

@Module
public abstract class AppModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);
}
