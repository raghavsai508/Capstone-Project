package com.example.android.favoritephotos.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.favoritephotos.FavoritesActivity;
import com.example.android.favoritephotos.R;
import com.example.android.favoritephotos.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private final Context mContext;
    private Cursor mCursor;


    public FavoritesAdapter(@NonNull Context context) {
        mContext = context;
    }


    public class FavoritesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_photo)
        ImageView imageViewPhoto;

        @BindView(R.id.btn_favorite)
        ImageView imageViewFavorite;

        public FavoritesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder favoritesViewHolder, int position) {
        mCursor.moveToPosition(position);

        String url_m = mCursor.getString(FavoritesActivity.INDEX_FAVORITE_URL_M);
//        String internal_url = mCursor.getString(FavoritesActivity.INDEX_FAVORITE_INTERNAL_URL);
        File internal_url = ImageUtils.getImageFileDownloaded(mContext, url_m,mContext.getString(R.string.image_directory));
        Picasso.get().load(internal_url).into(favoritesViewHolder.imageViewPhoto);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) {
            return 0;
        }
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

}
