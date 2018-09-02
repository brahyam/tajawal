package com.dvipersquad.tajawal.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Immutable model class for an Image.
 */
@Entity(tableName = "images",
        foreignKeys = {
                @ForeignKey(
                        entity = Hotel.class,
                        parentColumns = "hotelId",
                        childColumns = "hotel_id_fk"
                )})
public class HotelImage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private final String url;

    @NonNull
    @ColumnInfo(name = "hotel_id_fk")
    private int hotelId;

    public HotelImage(int id, @NonNull String url, @NonNull int hotelId) {
        this.id = id;
        this.url = url;
        this.hotelId = hotelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(@NonNull int hotelId) {
        this.hotelId = hotelId;
    }
}
