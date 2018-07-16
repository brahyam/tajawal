package com.dvipersquad.tajawal.data;

/**
 * Created by Brahyam on 15-Jul-18.
 */

public class HotelSummary {

    private final Float highRate;

    private final Float lowRate;

    private final String hotelName;

    public HotelSummary(Float highRate, Float lowRate, String hotelName) {
        this.highRate = highRate;
        this.lowRate = lowRate;
        this.hotelName = hotelName;
    }

    public Float getHighRate() {
        return highRate;
    }

    public Float getLowRate() {
        return lowRate;
    }

    public String getHotelName() {
        return hotelName;
    }
}
