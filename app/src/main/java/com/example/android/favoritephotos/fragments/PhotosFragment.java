package com.example.android.favoritephotos.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.favoritephotos.DetailActivity;
import com.example.android.favoritephotos.R;
import com.example.android.favoritephotos.adapters.PhotosAdapter;
import com.example.android.favoritephotos.interfaces.PhotoItemClickListener;
import com.example.android.favoritephotos.models.FlickrPhoto;
import com.example.android.favoritephotos.network.PhotosLoaderCallbacks;
import com.example.android.favoritephotos.utils.JsonUtils;
import com.example.android.favoritephotos.utils.NetworkUtility;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotosFragment extends Fragment implements PhotosLoaderCallbacks.PhotosLoaderListener, OnMapReadyCallback {

    private static final int ID_PHOTOS_DATALOADER = 111;

    private static final String INTENT_MARKER_FLICKR_PHOTO = "intent_marker_flickr_photo";

    private List<FlickrPhoto> photosList;
    private GoogleMap mMap;
    private LatLng latLng;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.recyclerView_photos)
    RecyclerView recyclerViewPhotos;


    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context context = getContext();
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, rootView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        recyclerViewPhotos.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 2);
        recyclerViewPhotos.setLayoutManager(linearLayoutManager);

        getPhotos();

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }


    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    private void getPhotos() {
        URL photosURL = NetworkUtility.buildFlickrUrl(1, latLng);
        PhotosLoaderCallbacks photosLoaderCallbacks = new PhotosLoaderCallbacks(getContext(), photosURL, this);
        try {
            getActivity().getSupportLoaderManager().initLoader(ID_PHOTOS_DATALOADER, null, photosLoaderCallbacks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPhotos() {
        if (photosList == null) {
            return;
        }

        PhotosAdapter photosAdapter = new PhotosAdapter(getContext(), photosList, new PhotoItemClickListener() {
            @Override
            public void onPhotoClick(FlickrPhoto photo) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(INTENT_MARKER_FLICKR_PHOTO, photo);
                startActivity(intent);
            }
        });
        recyclerViewPhotos.setAdapter(photosAdapter);
    }


    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(String jsonString) {
        try {
            getActivity().getSupportLoaderManager().destroyLoader(ID_PHOTOS_DATALOADER);
            photosList = JsonUtils.getFlickrPhotos(jsonString);
            Log.d("Photos ", "onPostExecute: "+ photosList.toString());
            loadPhotos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
