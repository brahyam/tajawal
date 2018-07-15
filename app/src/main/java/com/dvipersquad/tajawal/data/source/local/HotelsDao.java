package com.dvipersquad.tajawal.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dvipersquad.tajawal.data.Hotel;

import java.util.List;

/**
 * Data Access Object for the hotels table.
 */
@Dao
public interface HotelsDao {

    /**
     * Select all hotels from the hotels table.
     *
     * @return all hotels.
     */
    @Query("SELECT * FROM Hotels")
    List<Hotel> getHotels();

    /**
     * Select a hotel by id.
     *
     * @param hotelId the hotel id.
     * @return the hotel with hotelId.
     */
    @Query("SELECT * FROM Hotels WHERE hotelId = :hotelId")
    Hotel getHotelById(Integer hotelId);

    /**
     * Insert a hotel in the database. If the hotel already exists, replace it.
     *
     * @param hotel the hotel to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHotel(Hotel hotel);

    /**
     * Update a hotel.
     *
     * @param hotel hotel to be updated
     * @return the number of hotels updated. This should always be 1.
     */
    @Update
    int updateHotel(Hotel hotel);

    /**
     * Delete a hotel by id.
     *
     * @return the number of hotels deleted. This should always be 1.
     */
    @Query("DELETE FROM Hotels WHERE hotelId = :hotelId")
    void deleteHotelById(Integer hotelId);

    /**
     * Delete all hotels.
     */
    @Query("DELETE FROM Hotels")
    void deleteHotels();
}
