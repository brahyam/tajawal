package com.dvipersquad.tajawal.hotels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dvipersquad.tajawal.R;
import com.dvipersquad.tajawal.data.Hotel;
import com.dvipersquad.tajawal.di.ActivityScoped;
import com.dvipersquad.tajawal.hoteldetails.HotelDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Displays a lits of hotels
 */
@ActivityScoped
public class HotelsFragment extends DaggerFragment implements HotelsContract.View {

    @Inject
    HotelsContract.Presenter presenter;

    /**
     * Listens for clicks on list elements
     */
    HotelItemListener listener = new HotelItemListener() {
        @Override
        public void onHotelClick(Hotel clickedHotel) {
            presenter.openHotelDetails(clickedHotel);
        }
    };

    private HotelAdapter adapter;
    private TextView txtNoHotels;
    private RecyclerView recyclerHotels;
    private ProgressBar progBarHotelsLoading;

    @Inject
    public HotelsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HotelAdapter(new ArrayList<Hotel>(), listener);
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
        View root = inflater.inflate(R.layout.hotels_frag, container, false);

        // Set up recyclerview hotel list
        recyclerHotels = root.findViewById(R.id.recyclerHotels);
        recyclerHotels.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHotels.setLayoutManager(linearLayoutManager);
        recyclerHotels.setAdapter(adapter);
        // Set up no hotels views
        txtNoHotels = root.findViewById(R.id.txtNoHotels);
        progBarHotelsLoading = root.findViewById(R.id.progBarHotelsLoading);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                presenter.loadHotels(true);
                break;
        }
        return true;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            progBarHotelsLoading.setVisibility(View.VISIBLE);
        } else {
            progBarHotelsLoading.setVisibility(View.GONE);
        }

    }

    @Override
    public void showHotels(List<Hotel> hotels) {
        adapter.replaceData(hotels);
        recyclerHotels.setVisibility(View.VISIBLE);
        txtNoHotels.setVisibility(View.GONE);
    }

    @Override
    public void showHotelDetailsUI(Integer hotelId) {
        Intent intent = new Intent(getContext(), HotelDetailsActivity.class);
        intent.putExtra(HotelDetailsActivity.EXTRA_HOTEL_ID, hotelId);
        startActivity(intent);
    }

    @Override
    public void showLoadingHotelsError() {
        showMessage(getString(R.string.error_loading_hotels));
    }

    @Override
    public void showNoHotels() {
        recyclerHotels.setVisibility(View.GONE);
        txtNoHotels.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public interface HotelItemListener {

        void onHotelClick(Hotel clickedHotel);
    }

    private class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

        private List<Hotel> hotels;
        private HotelItemListener listener;

        public HotelAdapter(List<Hotel> hotels, HotelItemListener listener) {
            this.hotels = hotels;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.hotels_list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Hotel hotel = hotels.get(position);

            if (hotel.getSummary() != null) {
                holder.txtHotelName.setText(hotel.getSummary().getHotelName());
            }

            if (hotel.getImage() != null && !hotel.getImage().isEmpty()) {
                Picasso.get()
                        .load(hotel.getImage().get(0).getUrl())
                        .resizeDimen(R.dimen.recycler_row_thumbnail_width, R.dimen.recycler_row_height)
                        .centerCrop()
                        .into(holder.imgHotelThumbnail);
            }

            if (listener != null) {
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onHotelClick(hotel);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return hotels.size();
        }

        public void replaceData(List<Hotel> hotels) {
            this.hotels = hotels;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public View layout;
            public ImageView imgHotelThumbnail;
            public TextView txtHotelName;

            public ViewHolder(View itemView) {
                super(itemView);
                layout = itemView;
                txtHotelName = itemView.findViewById(R.id.txtHotelName);
                imgHotelThumbnail = itemView.findViewById(R.id.imgHotelThumbnail);
            }
        }
    }
}
