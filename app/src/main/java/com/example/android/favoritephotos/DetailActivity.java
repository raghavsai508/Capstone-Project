package com.example.android.favoritephotos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    private static final String INTENT_MARKER_FLICKR_PHOTO = "intent_marker_flickr_photo";
    private static final String INTENT_MARKER_FLICKR_URL = "flickr_url";
    private static final String IS_INTERNAL = "isInternal";

    private String photoUrl;
    private String flickrPhotoUrl;
    private boolean isInternal = false;

    @BindView(R.id.imageView_photo_detail)
    ImageView imageViewPhoto;

    @BindView(R.id.btnShare)
    FloatingActionButton btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentCalled = getIntent();
        if (intentCalled != null) {
            photoUrl = intentCalled.getStringExtra(INTENT_MARKER_FLICKR_PHOTO);
            flickrPhotoUrl = intentCalled.getStringExtra(INTENT_MARKER_FLICKR_URL);
            if (intentCalled.hasExtra(IS_INTERNAL)) {
                isInternal = true;
                File filePath = new File(photoUrl);
                Picasso.get().load(filePath).into(imageViewPhoto);
            } else {
                Picasso.get().load(photoUrl).into(imageViewPhoto);
            }
        }
    }

    @OnClick(R.id.btnShare)
    void btnShareAction(View view) {
        Intent myShareIntent;
//        if (isInternal) {
//            myShareIntent = new Intent(Intent.ACTION_VIEW);
//            myShareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            myShareIntent.setDataAndType(Uri.parse(flickrPhotoUrl), "image/png");
//            PackageManager packageManager = getPackageManager();
//            if (myShareIntent.resolveActivity(packageManager) != null) {
//                startActivity(myShareIntent);
//            }
//        } else {
            myShareIntent = new Intent(Intent.ACTION_SEND);
            myShareIntent.setType("image/*");
//            myShareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            myShareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(flickrPhotoUrl));
//            myShareIntent.setDataAndType(Uri.parse(flickrPhotoUrl), "image/*");
            startActivity(Intent.createChooser(myShareIntent, "Share Image"));
//            /data/user/0/com.example.android.favoritephotos/app_favorites_directory/43600873140_d8e75ce30b.jpg
//        }

//        myShareIntent = new Intent(Intent.ACTION_SEND);
//        myShareIntent.addFlags(
//                Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        Uri fileUri;
//        File requestFile = new File(flickrPhotoUrl);
//        try {
//            fileUri = FileProvider.getUriForFile(
//                    DetailActivity.this,
//                    getString(R.string.content_authority),
//                    requestFile);
//            myShareIntent.setDataAndType(fileUri, "image/*");
//            startActivity(Intent.createChooser(myShareIntent, "Share Image"));
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            Log.e("File Selector",
//                    "The selected file can't be shared: " + requestFile.toString());
//        }



    }

}
