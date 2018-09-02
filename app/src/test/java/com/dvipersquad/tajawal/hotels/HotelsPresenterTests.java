package com.dvipersquad.tajawal.hotels;

import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.data.HotelImage;
import com.dvipersquad.tajawal.data.HotelLocation;
import com.dvipersquad.tajawal.data.HotelSummary;
import com.dvipersquad.tajawal.data.source.HotelsDataSource;
import com.dvipersquad.tajawal.data.source.HotelsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link HotelsPresenter}
 */
public class HotelsPresenterTests {

    private static List<Hotel> HOTELS;

    @Mock
    private HotelsRepository hotelsRepository;

    @Mock
    private HotelsContract.View hotelsView;

    @Captor
    private ArgumentCaptor<HotelsDataSource.LoadHotelsCallback> loadHotelsCallbackCaptor;

    private HotelsPresenter hotelsPresenter;

    @Before
    public void setupHotelsPresenter() {
        MockitoAnnotations.initMocks(this);

        hotelsPresenter = new HotelsPresenter(hotelsRepository);
        hotelsPresenter.takeView(hotelsView);

        when(hotelsView.isActive()).thenReturn(true);

        HOTELS = new ArrayList<Hotel>() {{
            add(new Hotel(1,
                    new ArrayList<HotelImage>() {{
                        add(new HotelImage(1, "https://az712897.vo", 1));
                    }},
                    new HotelLocation("Test Address 1", 1.1f, 1.2f),
                    new HotelSummary(10.1f, 5.1f, "Hotel Name 1")));

            add(new Hotel(2,
                    new ArrayList<HotelImage>() {{
                        add(new HotelImage(2, "https://az712896.vo", 2));
                    }},
                    new HotelLocation("Test Address 2", 1.1f, 1.2f),
                    new HotelSummary(10.1f, 5.1f, "Hotel Name 2")));

            add(new Hotel(3,
                    new ArrayList<HotelImage>() {{
                        add(new HotelImage(3, "https://az712895.vo", 3));
                    }},
                    new HotelLocation("Test Address 3", 1.1f, 1.2f),
                    new HotelSummary(10.1f, 5.1f, "Hotel Name 3")));
        }};
    }

    @Test
    public void loadAllHotelsFromRepositoryAndLoadIntoView() {
        // Request hotels load
        hotelsPresenter.loadHotels(true);

        // Called 2 times. On fragment added/ On load requested
        verify(hotelsRepository, times(2))
                .getHotels(loadHotelsCallbackCaptor.capture());
        loadHotelsCallbackCaptor.getValue().onHotelsLoaded(HOTELS);

        // Progress indicator is shown
        verify(hotelsView, times(2)).setLoadingIndicator(true);
        // Progress indicator is hidden and hotels are shown
        verify(hotelsView, times(1)).setLoadingIndicator(false);
        ArgumentCaptor<List> showHotelsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(hotelsView).showHotels(showHotelsArgumentCaptor.capture());
        assertTrue(showHotelsArgumentCaptor.getValue().size() == 3);
    }

    @Test
    public void clickOnTask_ShowsDetailUi() {
        // Given a hotel
        Hotel requestedHotel = new Hotel(3,
                new ArrayList<HotelImage>() {{
                    add(new HotelImage(3, "https://az712895.vo", 3));
                }},
                new HotelLocation("Test Address", 1.1f, 1.2f),
                new HotelSummary(10.1f, 5.1f, "Hotel Name"));

        // When open hotel details is requested
        hotelsPresenter.openHotelDetails(requestedHotel);

        // Then hotel detail UI is shown
        verify(hotelsView).showHotelDetailsUI(any(Integer.class));
    }


    @Test
    public void unavailableTasks_ShowsError() {
        // When hotels are loaded
        hotelsPresenter.loadHotels(true);

        // And the hotels aren't available in the repository
        verify(hotelsRepository, times(2)).getHotels(loadHotelsCallbackCaptor.capture());
        loadHotelsCallbackCaptor.getValue().onDataNotAvailable();

        // Then an error message is shown
        verify(hotelsView).showLoadingHotelsError();
    }

}
