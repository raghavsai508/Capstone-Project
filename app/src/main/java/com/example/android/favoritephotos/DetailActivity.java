package com.example.android.favoritephotos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.android.favoritephotos.models.FlickrPhoto;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String INTENT_MARKER_FLICKR_PHOTO = "intent_marker_flickr_photo";

    private FlickrPhoto flickrPhoto;

    @BindView(R.id.imageView_photo_detail)
    ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentCalled = getIntent();
        if (intentCalled != null) {
            flickrPhoto = intentCalled.getParcelableExtra(INTENT_MARKER_FLICKR_PHOTO);
            Picasso.get().load(flickrPhoto.getUrl_m()).into(imageViewPhoto);
        }

    }
}
