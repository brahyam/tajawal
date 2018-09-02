package com.dvipersquad.tajawal.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dvipersquad.tajawal.data.source.local.HotelsDao;
import com.dvipersquad.tajawal.data.source.local.HotelsDatabase;
import com.dvipersquad.tajawal.data.source.local.HotelsLocalDataSource;
import com.dvipersquad.tajawal.data.source.remote.HotelsRemoteDataSource;
import com.dvipersquad.tajawal.utils.AppExecutors;
import com.dvipersquad.tajawal.utils.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class HotelsRepositoryModule {

    private static final int THREAD_COUNT = 3;
    private String HOTELS_DB_NAME = "Hotels.db";

    @Singleton
    @Provides
    @Local
    HotelsDataSource provideHotelsLocalDataSource(HotelsDao dao, AppExecutors executors) {
        return new HotelsLocalDataSource(executors, dao);
    }

    @Singleton
    @Provides
    @Remote
    HotelsDataSource provideHotelsRemoteDataSource() {
        return new HotelsRemoteDataSource();
    }

    @Singleton
    @Provides
    HotelsDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), HotelsDatabase.class, HOTELS_DB_NAME).build();
    }

    @Singleton
    @Provides
    HotelsDao provideHotelsDao(HotelsDatabase db) {
        return db.hotelsDao();
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
