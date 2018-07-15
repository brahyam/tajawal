package com.dvipersquad.tajawal.data;

/**
 * Created by Brahyam on 15-Jul-18.
 */

public class HotelLocation {

    private final String address;

    private final Long latitude;

    private final Long longitude;

    public HotelLocation(String address, Long latitude, Long longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public Long getLatitude() {
        return latitude;
    }

    public Long getLongitude() {
        return longitude;
    }
}
