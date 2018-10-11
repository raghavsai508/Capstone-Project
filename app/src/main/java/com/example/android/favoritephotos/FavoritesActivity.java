package com.example.android.favoritephotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {


    public static final int INDEX_FAVORITE_CREATED_DATE = 0;
    public static final int INDEX_FAVORITE_URL_M = 1;
    public static final int INDEX_FAVORITE_INTERNAL_URL = 2;

    @BindView(R.id.recyclerView_favorites)
    RecyclerView recyclerViewFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);



    }
}
