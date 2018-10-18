package com.example.android.favoritephotos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String INTENT_MARKER_FLICKR_PHOTO = "intent_marker_flickr_photo";
    private static final String IS_INTERNAL = "isInternal";

    private String flickrPhotoUrl;

    @BindView(R.id.imageView_photo_detail)
    ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentCalled = getIntent();
        if (intentCalled != null) {
            flickrPhotoUrl = intentCalled.getStringExtra(INTENT_MARKER_FLICKR_PHOTO);
            if (intentCalled.hasExtra(IS_INTERNAL)) {
                File filePath = new File(flickrPhotoUrl);
                Picasso.get().load(filePath).into(imageViewPhoto);
            } else {
                Picasso.get().load(flickrPhotoUrl).into(imageViewPhoto);
            }
        }

    }
}
