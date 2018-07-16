package com.dvipersquad.tajawal.hotels;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.data.source.HotelsDataSource;
import com.dvipersquad.tajawal.data.source.HotelsRepository;
import com.dvipersquad.tajawal.di.ActivityScoped;

import java.util.List;

import javax.inject.Inject;

@ActivityScoped
final class HotelsPresenter implements HotelsContract.Presenter {

    private final HotelsRepository hotelsRepository;

    @Nullable
    private HotelsContract.View hotelsView;

    private boolean firstLoad = true;

    @Inject
    public HotelsPresenter(HotelsRepository hotelsRepository) {
        this.hotelsRepository = hotelsRepository;
    }

    @Override
    public void takeView(HotelsContract.View view) {
        this.hotelsView = view;
        loadHotels(false);
    }

    @Override
    public void dropView() {
        this.hotelsView = null;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadHotels(boolean forceUpdate) {
        loadHotels(forceUpdate || firstLoad, true);
        firstLoad = false;
    }

    private void loadHotels(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            if (hotelsView != null) {
                hotelsView.setLoadingIndicator(true);
            }
        }
        if (forceUpdate) {
            hotelsRepository.refreshHotels();
        }

        hotelsRepository.getHotels(new HotelsDataSource.LoadHotelsCallback() {
            @Override
            public void onHotelsLoaded(List<Hotel> hotels) {
                // View might be unavailable when response arrives
                if (hotelsView == null || !hotelsView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    hotelsView.setLoadingIndicator(false);
                }

                if (hotels.isEmpty()) {
                    hotelsView.showNoHotels();
                } else {
                    hotelsView.showHotels(hotels);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (hotelsView == null || !hotelsView.isActive()) {
                    return;
                }
                hotelsView.showLoadingHotelsError();
            }
        });
    }

    @Override
    public void openHotelDetails(@NonNull Hotel requestedHotel) {
        if (hotelsView != null) {
            hotelsView.showHotelDetailsUI(requestedHotel.getHotelId());
        }
    }
}
