package com.dvipersquad.tajawal.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable model class for a Hotel.
 */
@Entity(tableName = "hotels")
public class Hotel {

    @PrimaryKey
    @NonNull
    private Integer hotelId;

    @Nullable
    @Ignore
    @Relation(parentColumn = "hotelId", entityColumn = "hotel_id_fk")
    private List<HotelImage> image;

    @Nullable
    @Embedded
    private HotelLocation location;

    @Nullable
    @Embedded
    private HotelSummary summary;

    public Hotel(@NonNull Integer hotelId, HotelLocation location, HotelSummary summary) {
        this.hotelId = hotelId;
        this.image = new ArrayList<>();
        this.location = location;
        this.summary = summary;
    }

    public Hotel(@NonNull Integer hotelId, List<HotelImage> image, HotelLocation location, HotelSummary summary) {
        this.hotelId = hotelId;
        this.image = image;
        this.location = location;
        this.summary = summary;
    }

    @NonNull
    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(@NonNull Integer hotelId) {
        this.hotelId = hotelId;
    }

    @Nullable
    public List<HotelImage> getImage() {
        return image;
    }

    public void setImage(@Nullable List<HotelImage> image) {
        this.image = image;
    }

    @Nullable
    public HotelLocation getLocation() {
        return location;
    }

    public void setLocation(@Nullable HotelLocation location) {
        this.location = location;
    }

    @Nullable
    public HotelSummary getSummary() {
        return summary;
    }

    public void setSummary(@Nullable HotelSummary summary) {
        this.summary = summary;
    }
}
