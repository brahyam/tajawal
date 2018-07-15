package com.dvipersquad.tajawal.data.source.remote;

import android.support.annotation.NonNull;

import com.dvipersquad.tajawal.data.Hotel;
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

    private final static String API_URL = "http://gchbib.de/tajawal";
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
        Call<List<Hotel>> call = hotelsApi.getHotels();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    callback.onHotelsLoaded(response.body());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
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
        @GET("/hotels_exercise.json")
        Call<List<Hotel>> getHotels();
    }
}
