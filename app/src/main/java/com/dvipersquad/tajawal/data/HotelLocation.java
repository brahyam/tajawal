package com.dvipersquad.tajawal.data;

/**
 * Created by Brahyam on 15-Jul-18.
 */

public class HotelLocation {

    private final String address;

    private final Float latitude;

    private final Float longitude;

    public HotelLocation(String address, Float latitude, Float longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
}
