package com.example.android.favoritephotos.interfaces;

import com.example.android.favoritephotos.models.FlickrPhoto;

public interface PhotoItemClickListener {
    void onPhotoClick(FlickrPhoto photo);
    void onFavoriteClick(String mediaURL, boolean isFavorite);
}
