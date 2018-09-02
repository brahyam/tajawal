package com.dvipersquad.tajawal.di;

import android.app.Application;

import com.dvipersquad.tajawal.TajawalApp;
import com.dvipersquad.tajawal.data.source.HotelsRepository;
import com.dvipersquad.tajawal.data.source.HotelsRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {HotelsRepositoryModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<TajawalApp> {

    HotelsRepository getHotelsRepository();

    // Enable us to doDaggerAppComponent.builder().application(this).build().inject(this);
    // Includes application in graph
    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
