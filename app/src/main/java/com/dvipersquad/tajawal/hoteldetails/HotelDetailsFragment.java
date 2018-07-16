package com.dvipersquad.tajawal.hoteldetails;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvipersquad.tajawal.R;
import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.di.ActivityScoped;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * View for Hotel Details
 */
@ActivityScoped
public class HotelDetailsFragment extends DaggerFragment implements HotelDetailsContract.View {

    @NonNull
    private static final String ARGUMENT_HOTEL_ID = "HOTEL_ID";

    @Inject
    Integer hotelId;

    @Inject
    HotelDetailsContract.Presenter presenter;

    ImageView imgHotelDetailsCover;
    TextView txtHotelName;
    TextView txtHotelHighRate;
    TextView txtHotelLowRate;
    TextView txtHotelAddress;

    @Inject
    public HotelDetailsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hoteldetails_frag, container, false);
        imgHotelDetailsCover = root.findViewById(R.id.imgHotelDetailsCover);
        txtHotelName = root.findViewById(R.id.txtHotelName);
        txtHotelHighRate = root.findViewById(R.id.txtHotelHighRate);
        txtHotelLowRate = root.findViewById(R.id.txtHotelLowRate);
        txtHotelAddress = root.findViewById(R.id.txtHotelAddress);
        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            txtHotelName.setText("");
            txtHotelHighRate.setText("");
            txtHotelLowRate.setText(getString(R.string.loading));
            txtHotelAddress.setText("");
        }
    }

    @Override
    public void showHotel(Hotel hotel) {
        if (hotel.getImage() != null && hotel.getImage().size() > 0) {
            Picasso.get()
                    .load(hotel.getImage().get(0).getUrl())
                    .fit()
                    .centerCrop()
                    .into(imgHotelDetailsCover);
        }
        if (hotel.getSummary() != null) {
            txtHotelName.setText(hotel.getSummary().getHotelName());
            if (hotel.getSummary().getHighRate() != null) {
                txtHotelHighRate.setText(String.format(Locale.getDefault(), "%s %.2f", "SAR", hotel.getSummary().getHighRate()));
            } else {
                txtHotelHighRate.setText(String.format(Locale.getDefault(), "%s %s", "SAR", "XXX"));
            }
            txtHotelHighRate.setPaintFlags(txtHotelHighRate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (hotel.getSummary().getLowRate() != null) {
                txtHotelLowRate.setText(String.format(Locale.getDefault(), "%s %.2f", "SAR", hotel.getSummary().getLowRate()));
            } else {
                txtHotelLowRate.setText(String.format(Locale.getDefault(), "%s %s", "SAR", "XXX"));
            }
        }
        if (hotel.getLocation() != null) {
            txtHotelAddress.setText(hotel.getLocation().getAddress());
        }
    }

    @Override
    public void showMissingHotel() {
        txtHotelName.setText("");
        txtHotelHighRate.setText("");
        txtHotelLowRate.setText(getString(R.string.no_data_available));
        txtHotelAddress.setText("");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
