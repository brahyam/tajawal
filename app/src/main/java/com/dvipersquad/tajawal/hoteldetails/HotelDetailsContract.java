package com.dvipersquad.tajawal.hoteldetails;

import com.dvipersquad.tajawal.BasePresenter;
import com.dvipersquad.tajawal.BaseView;

/**
 * Created by Brahyam on 15-Jul-18.
 */

public interface HotelDetailsContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter<View> {
    }

}
