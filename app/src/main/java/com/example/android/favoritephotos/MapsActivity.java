package com.example.android.favoritephotos;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.example.android.favoritephotos.data.FavoritePhotosContract;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private GoogleMap mMap;
    private Uri mUri;

    private static final int ID_PINS_LOADER = 251;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mUri = FavoritePhotosContract.PinsEntry.CONTENT_URI;

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        mMap.setOnMarkerClickListener(this);
        getSupportLoaderManager().initLoader(ID_PINS_LOADER, null, this);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                ContentResolver contentResolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(FavoritePhotosContract.PinsEntry.COLUMN_PIN_LATITUDE, latLng.latitude);
                contentValues.put(FavoritePhotosContract.PinsEntry.COLUMN_PIN_LONGITUDE, latLng.longitude);

                Uri insertedUri = contentResolver.insert(mUri, contentValues);
                if(insertedUri != null) {
                    Toast.makeText(getBaseContext(), insertedUri.toString(), Toast.LENGTH_LONG).show();
                }

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng));
                marker.setTag(insertedUri);

            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.v("Map Point", marker.getTag().toString());

        return false;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, @Nullable Bundle bundle) {

        switch (loaderId) {
            case ID_PINS_LOADER:
                return new CursorLoader(this,
                        mUri,
                        null,
                        null ,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null) {
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            return;
        }

        try {
            while (data.moveToNext()) {
                int index;

                index = data.getColumnIndexOrThrow(FavoritePhotosContract.PinsEntry._ID);
                long pinID = data.getLong(index);

                index = data.getColumnIndexOrThrow(FavoritePhotosContract.PinsEntry.COLUMN_PIN_LATITUDE);
                double latitude = data.getDouble(index);

                index = data.getColumnIndexOrThrow(FavoritePhotosContract.PinsEntry.COLUMN_PIN_LONGITUDE);
                double longitude = data.getDouble(index);

                LatLng latLng = new LatLng(latitude, longitude);

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng));
                Uri markerUri = FavoritePhotosContract.PinsEntry.CONTENT_URI
                                    .buildUpon()
                                    .appendPath(String.valueOf(pinID))
                                    .build();
                marker.setTag(markerUri);
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        finally {
            data.close();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
