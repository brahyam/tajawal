package com.dvipersquad.tajawal.hoteldetails;

import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.data.HotelImage;
import com.dvipersquad.tajawal.data.HotelLocation;
import com.dvipersquad.tajawal.data.HotelSummary;
import com.dvipersquad.tajawal.data.source.HotelsDataSource;
import com.dvipersquad.tajawal.data.source.HotelsRepository;
import com.dvipersquad.tajawal.hoteldetails.HotelDetailsContract;
import com.dvipersquad.tajawal.hoteldetails.HotelDetailsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HotelDetailsPresenterTests {

    public static final Hotel HOTEL = new Hotel(2,
            new ArrayList<HotelImage>() {{
                add(new HotelImage(1, "https://az712897.vo", 1));
            }},
            new HotelLocation("Test Address 1", 1.1f, 1.2f),
            new HotelSummary(10.1f, 5.1f, "Hotel Name 1"));

    @Mock
    private HotelsRepository hotelsRepository;

    @Mock
    private HotelDetailsContract.View hotelDetailsView;

    @Captor
    private ArgumentCaptor<HotelsDataSource.GetHotelCallback> getHotelCallbackCaptor;

    private HotelDetailsPresenter hotelDetailsPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Presenter needs active view
        when(hotelDetailsView.isActive()).thenReturn(true);
    }

    @Test
    public void getHotelFromRepositoryAndLoadIntoView() {

        hotelDetailsPresenter = new HotelDetailsPresenter(
                HOTEL.getHotelId(), hotelsRepository);
        hotelDetailsPresenter.takeView(hotelDetailsView);

        // Verify loading indicator is activated
        verify(hotelsRepository).getHotel(eq(HOTEL.getHotelId()), getHotelCallbackCaptor.capture());
        InOrder inOrder = inOrder(hotelDetailsView);
        inOrder.verify(hotelDetailsView).setLoadingIndicator(true);

        getHotelCallbackCaptor.getValue().onHotelLoaded(HOTEL);

        // Verify loading indicator is disabled
        inOrder.verify(hotelDetailsView).setLoadingIndicator(false);
        // Hotel is shown
        verify(hotelDetailsView).showHotel(HOTEL);
    }

    @Test
    public void getInvalidHotelFromRepositoryAndLoadIntoView() {
        // When loading of a hotel is requested with an invalid hotel ID.
        hotelDetailsPresenter = new HotelDetailsPresenter(
                null, hotelsRepository);
        hotelDetailsPresenter.takeView(hotelDetailsView);
        verify(hotelDetailsView).showMissingHotel();
    }

    @Test
    public void clickOnPhoto_ShowsFullScreenPhoto() {
        hotelDetailsPresenter = new HotelDetailsPresenter(
                HOTEL.getHotelId(), hotelsRepository);
        hotelDetailsPresenter.takeView(hotelDetailsView);

        // When open hotel photo is requested
        hotelDetailsPresenter.showHotelPhotoFullScreen(HOTEL);
        // Then Full Screen Photo UI is shown
        verify(hotelDetailsView).showPhotoFullScreenUI(any(String.class));
    }

    @Test
    public void clickOnBookButton_ShowsWebSite() {
        hotelDetailsPresenter = new HotelDetailsPresenter(
                HOTEL.getHotelId(), hotelsRepository);
        hotelDetailsPresenter.takeView(hotelDetailsView);
        // When booking button is pressed
        hotelDetailsPresenter.showBooking();
        // Then website is opened
        verify(hotelDetailsView).showWebsite(any(String.class));
    }

}
