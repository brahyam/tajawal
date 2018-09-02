package com.dvipersquad.tajawal.hoteldetails;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.data.source.HotelsDataSource;
import com.dvipersquad.tajawal.data.source.HotelsRepository;

import javax.inject.Inject;

final class HotelDetailsPresenter implements HotelDetailsContract.Presenter {

    private HotelsRepository hotelsRepository;

    @Nullable
    private HotelDetailsContract.View hotelDetailsView;

    @Nullable
    private Integer hotelId;
    private static final String BOOKING_URL = "https://www.tajawal.com/en/hotels-home";

    @Inject
    HotelDetailsPresenter(@Nullable Integer hotelId, HotelsRepository hotelsRepository) {
        this.hotelsRepository = hotelsRepository;
        this.hotelId = hotelId;
    }

    private void loadHotel() {
        if (hotelId == null) {
            if (hotelDetailsView != null) {
                hotelDetailsView.showMissingHotel();
            }
            return;
        }

        if (hotelDetailsView != null) {
            hotelDetailsView.setLoadingIndicator(true);
        }

        hotelsRepository.getHotel(hotelId, new HotelsDataSource.GetHotelCallback() {
            @Override
            public void onHotelLoaded(Hotel hotel) {
                if (hotelDetailsView == null || !hotelDetailsView.isActive()) {
                    return;
                }
                hotelDetailsView.setLoadingIndicator(false);
                if (hotel == null) {
                    hotelDetailsView.showMissingHotel();
                } else {
                    hotelDetailsView.showHotel(hotel);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (hotelDetailsView == null || !hotelDetailsView.isActive()) {
                    return;
                }
                hotelDetailsView.showMissingHotel();
            }
        });
    }

    @Override
    public void takeView(HotelDetailsContract.View view) {
        hotelDetailsView = view;
        loadHotel();
    }

    @Override
    public void dropView() {
        hotelDetailsView = null;
    }

    @Override
    public void showHotelPhotoFullScreen(@NonNull Hotel hotel) {
        if (hotelDetailsView != null && hotel.getImage() != null && hotel.getImage().size() > 0) {
            hotelDetailsView.showPhotoFullScreenUI(hotel.getImage().get(0).getUrl());
        }
    }

    @Override
    public void showBooking() {
        if (hotelDetailsView != null) {
            hotelDetailsView.showWebsite(BOOKING_URL);
        }
    }
}
