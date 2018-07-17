package com.dvipersquad.tajawal.data.source;

import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.data.HotelImage;
import com.dvipersquad.tajawal.data.HotelLocation;
import com.dvipersquad.tajawal.data.HotelSummary;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HotelsRepositoryTests {

    private static List<Hotel> HOTELS = new ArrayList<Hotel>() {{
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

    private HotelsRepository hotelsRepository;

    @Mock
    private HotelsDataSource hotelsRemoteDataSource;

    @Mock
    private HotelsDataSource hotelsLocalDataSource;

    @Mock
    private HotelsDataSource.GetHotelCallback getHotelCallback;

    @Mock
    private HotelsDataSource.LoadHotelsCallback loadHotelsCallback;

    @Captor
    private ArgumentCaptor<HotelsDataSource.LoadHotelsCallback> loadHotelsCallbackCaptor;

    @Captor
    private ArgumentCaptor<HotelsDataSource.GetHotelCallback> getHotelCallbackCaptor;

    @Before
    public void setupHotelsRepository() {
        MockitoAnnotations.initMocks(this);
        hotelsRepository = new HotelsRepository(hotelsRemoteDataSource, hotelsLocalDataSource);
    }

    @Test
    public void getHotels_repositoryCachesAfterFirstApiCall() {
        // send 2 calls to repo
        twoHotelsLoadCallsToRepository(loadHotelsCallback);

        // Then hotels were only requested once from Service API
        verify(hotelsRemoteDataSource).getHotels(any(HotelsDataSource.LoadHotelsCallback.class));
    }

    @Test
    public void getHotels_requestsAllHotelsFromLocalDataSource() {
        // When hotels are requested from the hotels repository
        hotelsRepository.getHotels(loadHotelsCallback);

        // Then hotels are loaded from the local data source
        verify(hotelsLocalDataSource).getHotels(any(HotelsDataSource.LoadHotelsCallback.class));
    }

    @Test
    public void saveHotel_savesHotelToLocalDB() {
        Hotel newHotel = new Hotel(1,
                new ArrayList<HotelImage>() {{
                    add(new HotelImage(1, "https://az712897.vo", 1));
                }},
                new HotelLocation("Test Address 1", 1.1f, 1.2f),
                new HotelSummary(10.1f, 5.1f, "Hotel Name 1"));

        // When a hotel is saved
        hotelsRepository.saveHotel(newHotel);

        // Then hotel is saved locally and cache is updated
        verify(hotelsLocalDataSource).saveHotel(newHotel);
        assertThat(hotelsRepository.cachedHotels.size(), is(1));
    }

    @Test
    public void getHotel_requestsSingleHotelFromLocalDataSource() {
        // When a hotel is requested from the hotels repository
        hotelsRepository.getHotel(2, getHotelCallback);

        // Then the hotel is loaded from the database
        verify(hotelsLocalDataSource).getHotel(eq(2), any(
                HotelsDataSource.GetHotelCallback.class));
    }

    @Test
    public void deleteAllHotels_deleteHotelsFromLocalDBAndUpdatesCache() {
        Hotel newHotel1 = new Hotel(1,
                new ArrayList<HotelImage>() {{
                    add(new HotelImage(1, "https://az712897.vo", 1));
                }},
                new HotelLocation("Test Address 1", 1.1f, 1.2f),
                new HotelSummary(10.1f, 5.1f, "Hotel Name 1"));
        hotelsRepository.saveHotel(newHotel1);
        Hotel newHotel2 = new Hotel(2,
                new ArrayList<HotelImage>() {{
                    add(new HotelImage(2, "https://az712896.vo", 2));
                }},
                new HotelLocation("Test Address 2", 1.1f, 1.2f),
                new HotelSummary(10.1f, 5.1f, "Hotel Name 2"));
        hotelsRepository.saveHotel(newHotel2);
        Hotel newHotel3 = new Hotel(3,
                new ArrayList<HotelImage>() {{
                    add(new HotelImage(3, "https://az712895.vo", 3));
                }},
                new HotelLocation("Test Address 3", 1.1f, 1.2f),
                new HotelSummary(10.1f, 5.1f, "Hotel Name 3"));
        hotelsRepository.saveHotel(newHotel3);

        // When all hotels are deleted to the hotels repository
        hotelsRepository.deleteAllHotels();

        // Verify the data sources were called
        verify(hotelsLocalDataSource).deleteAllHotels();
        // And cache is erased
        assertThat(hotelsRepository.cachedHotels.size(), is(0));
    }

    @Test
    public void deleteHotel_deleteHotelFromLocalDBAndRemovedFromCache() {
        // Given a hotel in the repository
        Hotel newHotel1 = new Hotel(1,
                new ArrayList<HotelImage>() {{
                    add(new HotelImage(1, "https://az712897.vo", 1));
                }},
                new HotelLocation("Test Address 1", 1.1f, 1.2f),
                new HotelSummary(10.1f, 5.1f, "Hotel Name 1"));
        hotelsRepository.saveHotel(newHotel1);
        assertThat(hotelsRepository.cachedHotels.containsKey(newHotel1.getHotelId()), is(true));

        // When deleted
        hotelsRepository.deleteHotel(newHotel1.getHotelId());

        // Verify the local data source is called
        verify(hotelsLocalDataSource).deleteHotel(newHotel1.getHotelId());

        // Verify it's removed from cache
        assertThat(hotelsRepository.cachedHotels.containsKey(newHotel1.getHotelId()), is(false));
    }

    @Test
    public void getHotelsWithDirtyCache_HotelsAreRetrievedFromRemote() {
        // Request hotels with dirty cache
        hotelsRepository.refreshHotels();
        hotelsRepository.getHotels(loadHotelsCallback);

        // And the remote data source has data available
        setHotelsAvailable(hotelsRemoteDataSource, HOTELS);

        // Verify the hotels from the remote data source are returned, not the local
        verify(hotelsLocalDataSource, never()).getHotels(loadHotelsCallback);
        verify(loadHotelsCallback).onHotelsLoaded(HOTELS);
    }

    @Test
    public void getHotelsWithLocalDataSourceUnavailable_hotelsAreRetrievedFromRemote() {
        // Request hotels
        hotelsRepository.getHotels(loadHotelsCallback);

        // And the local data source has no data available
        setHotelsNotAvailable(hotelsLocalDataSource);

        // And the remote data source has data available
        setHotelsAvailable(hotelsRemoteDataSource, HOTELS);

        // Verify the hotels from the local data source are returned
        verify(loadHotelsCallback).onHotelsLoaded(HOTELS);
    }

    @Test
    public void getHotelsWithBothDataSourcesUnavailable_firesOnDataUnavailable() {
        // Request hotels
        hotelsRepository.getHotels(loadHotelsCallback);

        // data not available locally
        setHotelsNotAvailable(hotelsLocalDataSource);

        // data not available remotely
        setHotelsNotAvailable(hotelsRemoteDataSource);

        // Verify no data is returned
        verify(loadHotelsCallback).onDataNotAvailable();
    }

    @Test
    public void getHotelWithBothDataSourcesUnavailable_firesOnDataUnavailable() {
        // Given a wrong hotel id
        final Integer hotelId = 215;

        // Request wrong hotel
        hotelsRepository.getHotel(hotelId, getHotelCallback);

        // Data is not available locally
        setHotelNotAvailable(hotelsLocalDataSource, hotelId);

        // Verify no data is returned
        verify(getHotelCallback).onDataNotAvailable();
    }

    @Test
    public void getHotels_refreshesLocalDataSource() {
        // Force remote reload
        hotelsRepository.refreshHotels();

        // Request hotels
        hotelsRepository.getHotels(loadHotelsCallback);

        // Fake remote data source response
        setHotelsAvailable(hotelsRemoteDataSource, HOTELS);

        // Verify data fetched was saved in DB.
        verify(hotelsLocalDataSource, times(HOTELS.size())).saveHotel(any(Hotel.class));
    }

    /**
     * Sends two calls to the hotels repository
     */
    private void twoHotelsLoadCallsToRepository(HotelsDataSource.LoadHotelsCallback callback) {
        // hotels are requested
        hotelsRepository.getHotels(callback); // First call to API

        // capture the callback
        verify(hotelsLocalDataSource).getHotels(loadHotelsCallbackCaptor.capture());

        // Local data source doesn't have data yet
        loadHotelsCallbackCaptor.getValue().onDataNotAvailable();

        // Verify the remote data source is queried
        verify(hotelsRemoteDataSource).getHotels(loadHotelsCallbackCaptor.capture());

        // Trigger callback so hotels are cached
        loadHotelsCallbackCaptor.getValue().onHotelsLoaded(HOTELS);

        hotelsRepository.getHotels(callback); // Second call to API
    }

    private void setHotelsNotAvailable(HotelsDataSource dataSource) {
        verify(dataSource).getHotels(loadHotelsCallbackCaptor.capture());
        loadHotelsCallbackCaptor.getValue().onDataNotAvailable();
    }

    private void setHotelsAvailable(HotelsDataSource dataSource, List<Hotel> hotels) {
        verify(dataSource).getHotels(loadHotelsCallbackCaptor.capture());
        loadHotelsCallbackCaptor.getValue().onHotelsLoaded(hotels);
    }

    private void setHotelNotAvailable(HotelsDataSource dataSource, Integer hotelId) {
        verify(dataSource).getHotel(eq(hotelId), getHotelCallbackCaptor.capture());
        getHotelCallbackCaptor.getValue().onDataNotAvailable();
    }
}
