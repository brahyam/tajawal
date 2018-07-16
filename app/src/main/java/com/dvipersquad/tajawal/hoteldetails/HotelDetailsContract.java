package com.dvipersquad.tajawal.hoteldetails;

import com.dvipersquad.tajawal.BasePresenter;
import com.dvipersquad.tajawal.BaseView;
import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.hotels.HotelsContract;

/**
 * Specifies the contract between the view and the presenter
 */
public interface HotelDetailsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showHotel(Hotel hotel);

        void showMissingHotel();

        boolean isActive();
    }

    interface Presenter extends BasePresenter<View> {

        void takeView(HotelDetailsContract.View HotelDetailsFragment);

        void dropView();
    }

}
