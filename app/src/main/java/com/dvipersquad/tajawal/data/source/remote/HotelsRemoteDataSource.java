package com.dvipersquad.tajawal.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.data.HotelList;
import com.dvipersquad.tajawal.data.source.HotelsDataSource;
import com.dvipersquad.tajawal.data.source.local.HotelsDao;
import com.dvipersquad.tajawal.utils.AppExecutors;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


/**
 * Handles remote server calls to retrieve hotels
 */
@Singleton
public class HotelsRemoteDataSource implements HotelsDataSource {

    private final static String API_URL = "http://gchbib.de/tajawal/";
    private static final String TAG = HotelsRemoteDataSource.class.getSimpleName();
    private HotelsApi hotelsApi;

    public HotelsRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hotelsApi = retrofit.create(HotelsApi.class);
    }

    @Override
    public void getHotels(@NonNull final LoadHotelsCallback callback) {
        Call<HotelList> call = hotelsApi.getHotels();
        call.enqueue(new Callback<HotelList>() {
            @Override
            public void onResponse(Call<HotelList> call, Response<HotelList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onHotelsLoaded(response.body().getHotels());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<HotelList> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getHotel(@NonNull Integer hotelId, @NonNull GetHotelCallback callback) {
        // Not implemented by remote service
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveHotel(@NonNull Hotel hotel) {
        // Handled only locally
        throw new UnsupportedOperationException();
    }

    @Override
    public void refreshHotels() {
        // Handled only locally
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllHotels() {
        // Handled only locally
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteHotel(@NonNull Integer hotelId) {
        // Handled only locally
        throw new UnsupportedOperationException();
    }

    private interface HotelsApi {
        @GET("hotels_exercise.json")
        Call<HotelList> getHotels();
    }
}
