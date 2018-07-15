package com.dvipersquad.tajawal.hotels;

import com.dvipersquad.tajawal.BasePresenter;
import com.dvipersquad.tajawal.BaseView;

/**
 * Created by Brahyam on 15-Jul-18.
 */

public interface HotelsContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {

    }
}
