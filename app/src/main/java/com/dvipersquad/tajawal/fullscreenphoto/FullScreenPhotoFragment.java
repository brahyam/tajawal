package com.dvipersquad.tajawal.fullscreenphoto;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dvipersquad.tajawal.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FullScreenPhotoFragment extends DaggerFragment implements FullScreenPhotoContract.View {

    @NonNull
    private static final String ARGUMENT_PHOTO_URL = "PHOTO_URL";

    @Inject
    String photoUrl;

    @Inject
    FullScreenPhotoContract.Presenter presenter;

    View layoutContent;
    ImageView imgPhoto;

    @Inject
    public FullScreenPhotoFragment() {
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
        View root = inflater.inflate(R.layout.fullscreenphoto_frag, container, false);
        layoutContent = root.findViewById(R.id.layoutContent);
        imgPhoto = root.findViewById(R.id.imgPhoto);
        return root;
    }

    @Override
    public void showPhoto(String url) {
        Picasso.get()
                .load(url)
                .into(imgPhoto);
        layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.closePhoto();
            }
        });
    }

    @Override
    public void showMissingPhoto() {
        // TODO: implement missing photo message
    }

    @Override
    public void closePhotoUI() {
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
