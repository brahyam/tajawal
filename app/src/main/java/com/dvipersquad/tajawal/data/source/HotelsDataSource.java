package com.dvipersquad.tajawal.data.source;

import android.support.annotation.NonNull;

import com.dvipersquad.tajawal.data.Hotel;

import java.util.List;

/**
 * Entry point for accessing data
 */
public interface HotelsDataSource {

    interface LoadHotelsCallback {

        void onHotelsLoaded(List<Hotel> hotels);

        void onDataNotAvailable();
    }

    interface GetHotelCallback {

        void onHotelLoaded(Hotel hotel);

        void onDataNotAvailable();
    }

    void getHotels(@NonNull LoadHotelsCallback callback);

    void getHotel(@NonNull Integer hotelId, @NonNull GetHotelCallback callback);

    void saveHotel(@NonNull Hotel hotel);

    void refreshHotels();

    void deleteAllHotels();

    void deleteHotel(@NonNull Integer hotelId);
}
