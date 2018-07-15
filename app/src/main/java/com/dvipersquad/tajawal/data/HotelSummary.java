package com.dvipersquad.tajawal.data;

/**
 * Created by Brahyam on 15-Jul-18.
 */

public class HotelSummary {

    private final Long highRate;

    private final Long lowRate;

    private final String hotelName;

    public HotelSummary(Long highRate, Long lowRate, String hotelName) {
        this.highRate = highRate;
        this.lowRate = lowRate;
        this.hotelName = hotelName;
    }

    public Long getHighRate() {
        return highRate;
    }

    public Long getLowRate() {
        return lowRate;
    }

    public String getHotelName() {
        return hotelName;
    }
}
