package com.dvipersquad.tajawal.data.source.local;

import android.support.annotation.NonNull;

import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.data.source.HotelsDataSource;
import com.dvipersquad.tajawal.utils.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Handles access to DB
 */
@Singleton
public class HotelsLocalDataSource implements HotelsDataSource {

    private final HotelsDao dao;
    private final AppExecutors appExecutors;

    @Inject
    public HotelsLocalDataSource(@NonNull AppExecutors executors, @NonNull HotelsDao dao) {
        this.dao = dao;
        this.appExecutors = executors;
    }

    @Override
    public void getHotels(@NonNull final LoadHotelsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Hotel> hotels = dao.getHotels();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (hotels.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onHotelsLoaded(hotels);
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getHotel(@NonNull final Integer hotelId, @NonNull final GetHotelCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Hotel hotel = dao.getHotelById(hotelId);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (hotel != null) {
                            callback.onHotelLoaded(hotel);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveHotel(@NonNull final Hotel hotel) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                dao.insertHotel(hotel);
            }
        };
        appExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refreshHotels() {
        // Handled by repository
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllHotels() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                dao.deleteHotels();
            }
        };

        appExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteHotel(@NonNull final Integer hotelId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                dao.deleteHotelById(hotelId);
            }
        };

        appExecutors.diskIO().execute(deleteRunnable);
    }
}
