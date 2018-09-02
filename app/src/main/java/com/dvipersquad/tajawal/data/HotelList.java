package com.dvipersquad.tajawal.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelList {

    @SerializedName("hotel")
    private List<Hotel> hotels;

    public HotelList(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
