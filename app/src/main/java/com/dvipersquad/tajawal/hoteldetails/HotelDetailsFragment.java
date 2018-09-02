package com.dvipersquad.tajawal.hoteldetails;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvipersquad.tajawal.R;
import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.di.ActivityScoped;
import com.dvipersquad.tajawal.fullscreenphoto.FullScreenPhotoActivity;
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

    View viewOverlay;
    ImageView imgHotelDetailsCover;
    TextView txtHotelName;
    TextView txtHotelHighRate;
    TextView txtHotelLowRate;
    TextView txtHotelAddress;
    Button btnBookNow;

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
        viewOverlay = root.findViewById(R.id.viewOverlay);
        imgHotelDetailsCover = root.findViewById(R.id.imgHotelDetailsCover);
        txtHotelName = root.findViewById(R.id.txtHotelName);
        txtHotelHighRate = root.findViewById(R.id.txtHotelHighRate);
        txtHotelLowRate = root.findViewById(R.id.txtHotelLowRate);
        txtHotelAddress = root.findViewById(R.id.txtHotelAddress);
        btnBookNow = root.findViewById(R.id.btnBookNow);
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
    public void showHotel(final Hotel hotel) {
        // Load image
        if (hotel.getImage() != null && hotel.getImage().size() > 0) {
            Picasso.get()
                    .load(hotel.getImage().get(0).getUrl())
                    .fit()
                    .centerCrop()
                    .into(imgHotelDetailsCover);
        }
        // Load hotel Data if available
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
        // Set click listeners
        viewOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showHotelPhotoFullScreen(hotel);
            }
        });
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showBooking();
            }
        });
    }

    @Override
    public void showMissingHotel() {
        txtHotelName.setText("");
        txtHotelHighRate.setText("");
        txtHotelLowRate.setText(getString(R.string.no_data_available));
        txtHotelAddress.setText("");
    }

    @Override
    public void showPhotoFullScreenUI(String photoUrl) {
        Intent intent = new Intent(getContext(), FullScreenPhotoActivity.class);
        intent.putExtra(FullScreenPhotoActivity.EXTRA_PHOTO_URL, photoUrl);
        startActivity(intent);
    }

    @Override
    public void showWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
