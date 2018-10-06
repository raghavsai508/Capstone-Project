package com.example.android.favoritephotos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.favoritephotos.R;
import com.example.android.favoritephotos.interfaces.PhotoItemClickListener;
import com.example.android.favoritephotos.models.FlickrPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private Context mContext;
    private List<FlickrPhoto> mFlickrPhotosList;
    private PhotoItemClickListener photoItemClickListener;

    public PhotosAdapter(Context context, List<FlickrPhoto> photos, PhotoItemClickListener photoClickListener) {
        mContext = context;
        mFlickrPhotosList = photos;
        photoItemClickListener = photoClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_photo)
        ImageView imageViewPhoto;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mView = layoutInflater.inflate(R.layout.photo_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoItemClickListener.onPhotoClick(mFlickrPhotosList.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FlickrPhoto flickrPhoto = mFlickrPhotosList.get(position);
        Picasso.get().load(flickrPhoto.getUrl_m()).into(viewHolder.imageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return mFlickrPhotosList.size();
    }

}
