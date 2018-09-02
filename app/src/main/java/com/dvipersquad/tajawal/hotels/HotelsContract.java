package com.dvipersquad.tajawal.hotels;

import android.support.annotation.NonNull;

import com.dvipersquad.tajawal.BasePresenter;
import com.dvipersquad.tajawal.BaseView;
import com.dvipersquad.tajawal.data.Hotel;

import java.util.List;


/**
 * Contract between view and presenter
 */
public interface HotelsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showHotels(List<Hotel> hotels);

        void showHotelDetailsUI(Integer hotelId);

        void showLoadingHotelsError();

        void showNoHotels();

        boolean isActive();
    }

    interface Presenter extends BasePresenter<View> {

        void result(int requestCode, int resultCode);

        void loadHotels(boolean forceUpdate);

        void openHotelDetails(@NonNull Hotel requestedHotel);
    }
}
