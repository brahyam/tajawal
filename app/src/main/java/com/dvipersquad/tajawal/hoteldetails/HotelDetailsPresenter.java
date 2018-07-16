package com.dvipersquad.tajawal.hoteldetails;


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

    @Inject
    public HotelDetailsPresenter(@Nullable Integer hotelId, HotelsRepository hotelsRepository) {
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
}
