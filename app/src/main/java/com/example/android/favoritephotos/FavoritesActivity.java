package com.example.android.favoritephotos;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.favoritephotos.adapters.FavoritesAdapter;
import com.example.android.favoritephotos.data.FavoritePhotosContract;
import com.example.android.favoritephotos.widget.FavoritePhotosWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {


    public static final int INDEX_FAVORITE_CREATED_DATE = 0;
    public static final int INDEX_FAVORITE_URL_M = 1;
    public static final int INDEX_FAVORITE_INTERNAL_URL = 2;

    public static final String[] FAVORITES_PROJECTION = {
            FavoritePhotosContract.FavoritePhotosEntry.COLUMN_CREATED_AT,
            FavoritePhotosContract.FavoritePhotosEntry.COLUMN_MEDIA_URL,
            FavoritePhotosContract.FavoritePhotosEntry.COLUMN_MEDIA_URL_INTERNAL
    };

    private static final int ID_FAVORITES_LOADER = 44;

    private FavoritesAdapter mFavoritesAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    @BindView(R.id.recyclerView_favorites)
    RecyclerView recyclerViewFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        recyclerViewFavorites.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewFavorites.setLayoutManager(linearLayoutManager);

        mFavoritesAdapter = new FavoritesAdapter(this);
        recyclerViewFavorites.setAdapter(mFavoritesAdapter);
        getSupportLoaderManager().initLoader(ID_FAVORITES_LOADER, null, this);

//        setupAppWidget();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, @Nullable Bundle bundle) {
        switch (loaderId) {

            case ID_FAVORITES_LOADER:
                Uri favoritesUri = FavoritePhotosContract.FavoritePhotosEntry.CONTENT_URI;
                String sortOrder = FavoritePhotosContract.FavoritePhotosEntry.COLUMN_CREATED_AT + " DESC";

                return new CursorLoader(this,
                        favoritesUri,
                        FAVORITES_PROJECTION,
                        null,
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mFavoritesAdapter.swapCursor(cursor);
        if (mPosition == RecyclerView.NO_POSITION) {
            mPosition = 0;
        }
        recyclerViewFavorites.smoothScrollToPosition(mPosition);
        setupAppWidget();


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mFavoritesAdapter.swapCursor(null);
    }

    private void setupAppWidget() {

//        SharedPreferences.Editor sharedPreferencesEdit = this.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit();



//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FavoritePhotosWidgetProvider.class));
//        FavoritePhotosWidgetProvider.updateFavoriteWidget(this, appWidgetManager, appWidgetIds);
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        FavoritePhotosWidgetProvider.sendRefreshBroadcast(FavoritesActivity.this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }).start();

    }

}
