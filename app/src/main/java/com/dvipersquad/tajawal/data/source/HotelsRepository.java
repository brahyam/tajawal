package com.dvipersquad.tajawal.data.source;

import android.support.annotation.NonNull;

import com.dvipersquad.tajawal.data.Hotel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Retrieves data from different sources into a cache
 */
@Singleton
public class HotelsRepository implements HotelsDataSource {

    private final HotelsDataSource hotelsRemoteDataSource;

    private final HotelsDataSource hotelsLocalDataSource;

    Map<Integer, Hotel> cachedHotels;

    boolean cacheIsDirty = false;

    @Inject
    HotelsRepository(@Remote HotelsDataSource hotelsRemoteDataSource, @Local HotelsDataSource hotelsLocalDataSource) {
        this.hotelsRemoteDataSource = hotelsRemoteDataSource;
        this.hotelsLocalDataSource = hotelsLocalDataSource;
    }

    /**
     * Gets hotels from cache, or local/remote if cache is not available
     *
     * @param callback Callback to call when process is complete/failed
     */
    @Override
    public void getHotels(@NonNull final LoadHotelsCallback callback) {
        if (cachedHotels != null && !cacheIsDirty) {
            callback.onHotelsLoaded(new ArrayList<>(cachedHotels.values()));
            return;
        }

        if (cacheIsDirty) {
            getHotelsFromRemoteDataSource(callback);
        } else {
            hotelsLocalDataSource.getHotels(new LoadHotelsCallback() {
                @Override
                public void onHotelsLoaded(List<Hotel> hotels) {
                    refreshCache(hotels);
                    callback.onHotelsLoaded(new ArrayList<>(cachedHotels.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getHotelsFromRemoteDataSource(callback);
                }
            });
        }
    }

    /**
     * Gets hotel from cache, or local/remote data source if not available
     *
     * @param hotelId  hotel Id to search for
     * @param callback callback to execute if process is completes/fails
     */
    @Override
    public void getHotel(@NonNull final Integer hotelId, @NonNull final GetHotelCallback callback) {

        // Try cache first
        if (cachedHotels != null && !cachedHotels.isEmpty()) {
            final Hotel cachedHotel = cachedHotels.get(hotelId);
            if (cachedHotel != null) {
                callback.onHotelLoaded(cachedHotel);
                return;
            }
        }

        // Try local source
        hotelsLocalDataSource.getHotel(hotelId, new GetHotelCallback() {
            @Override
            public void onHotelLoaded(Hotel hotel) {
                if (cachedHotels == null) {
                    cachedHotels = new LinkedHashMap<>();
                }
                cachedHotels.put(hotel.getHotelId(), hotel);
                callback.onHotelLoaded(hotel);
            }

            @Override
            public void onDataNotAvailable() {
                // Get individual hotel not implemented by remote source
                // so dont try to fetch from server
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveHotel(@NonNull Hotel hotel) {
        hotelsLocalDataSource.saveHotel(hotel);
        if (cachedHotels == null) {
            cachedHotels = new LinkedHashMap<>();
        }
        cachedHotels.put(hotel.getHotelId(), hotel);
    }

    @Override
    public void refreshHotels() {
        cacheIsDirty = true;
    }

    @Override
    public void deleteAllHotels() {
        hotelsLocalDataSource.deleteAllHotels();
        if (cachedHotels == null) {
            cachedHotels = new LinkedHashMap<>();
        }
        cachedHotels.clear();
    }

    @Override
    public void deleteHotel(@NonNull Integer hotelId) {
        hotelsLocalDataSource.deleteHotel(hotelId);
        cachedHotels.remove(hotelId);
    }

    private void getHotelsFromRemoteDataSource(final LoadHotelsCallback callback) {
        hotelsRemoteDataSource.getHotels(new LoadHotelsCallback() {
            @Override
            public void onHotelsLoaded(List<Hotel> hotels) {
                refreshCache(hotels);
                refreshLocalDataSource(hotels);
                callback.onHotelsLoaded(new ArrayList<>(cachedHotels.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Adds provided hotels to cache
     *
     * @param hotels list of hotels to include
     */
    private void refreshCache(List<Hotel> hotels) {
        if (cachedHotels == null) {
            cachedHotels = new LinkedHashMap<>();
        }
        cachedHotels.clear();
        for (Hotel hotel : hotels) {
            cachedHotels.put(hotel.getHotelId(), hotel);
        }
        cacheIsDirty = false;
    }

    /**
     * Adds provided hotels to local data source
     *
     * @param hotels list of hotels to include
     */
    private void refreshLocalDataSource(List<Hotel> hotels) {
        hotelsLocalDataSource.deleteAllHotels();
        for (Hotel hotel : hotels) {
            hotelsLocalDataSource.saveHotel(hotel);
        }
    }

}
