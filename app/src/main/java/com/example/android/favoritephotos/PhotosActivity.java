package com.example.android.favoritephotos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.favoritephotos.fragments.PhotosFragment;
import com.google.android.gms.maps.model.LatLng;

public class PhotosActivity extends AppCompatActivity {

    private static final String INTENT_MARKER_LATLNG = "intent_marker_latlng";

    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        Intent intentCalled = getIntent();
        if (intentCalled != null) {
            latLng = intentCalled.getParcelableExtra(INTENT_MARKER_LATLNG);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        PhotosFragment photosFragment = (PhotosFragment) fragmentManager.findFragmentByTag(PhotosFragment.class.getSimpleName());
        if (photosFragment == null) {
            photosFragment = new PhotosFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.photos_container, photosFragment, PhotosFragment.class.getSimpleName())
                    .commit();
            photosFragment.setLatLng(latLng);
        }
    }
}
