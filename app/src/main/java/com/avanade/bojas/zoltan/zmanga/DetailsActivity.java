package com.avanade.bojas.zoltan.zmanga;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avanade.bojas.zoltan.zmanga.repository.DownloadsRepository;
import com.avanade.bojas.zoltan.zmanga.repository.FavoritesRepository;
import com.avanade.bojas.zoltan.zmanga.persistence.MangaDatabase;
import com.avanade.bojas.zoltan.zmanga.repository.ChaptersRepository;
import com.avanade.bojas.zoltan.zmanga.repository.MangaDetailsRepository;
import com.avanade.bojas.zoltan.zmanga.viewmodel.MangaTitleDetailsViewModel;
import com.avanade.bojas.zoltan.zmanga.viewmodel.MangaTitleDetailsViewModelFactory;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity implements ChaptersDownloadedListener {

    final static String CURRENT_MANGA_URL = "CURRENT_MANGA_URL";

    MangaDetails mMangaDetails;
    Map<String, ChapterDownloadInfo.Status> mStatusMap = new HashMap<>();
    MangaTitleDetailsViewModel mModel;
    String mUrl;



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CURRENT_MANGA_URL, mUrl);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState!=null) {
            mUrl = savedInstanceState.getString(CURRENT_MANGA_URL);
        } else {
            Intent intent = getIntent();
            mUrl = intent.getStringExtra("url");
        }

        ((ImageView) findViewById(R.id.detailsImageView)).setImageResource(R.drawable.ic_home_black_24dp);
        mModel = ViewModelProviders
                .of(this, new MangaTitleDetailsViewModelFactory(this, mUrl))
                .get(MangaTitleDetailsViewModel.class);

        Observer<Boolean> favoriteObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isFavorite) {
                Log.e("is favorite:" , "is favorite:" + isFavorite.toString());
                setFavoriteButton(isFavorite);
            }
        };

        mModel.isFavorite(mUrl).observe(this, favoriteObserver);

        Observer<MangaDetails> mangaDetailsObserver = new Observer<MangaDetails>() {
            @Override
            public void onChanged(@Nullable MangaDetails mangaDetails) {
                Log.e("details", "manga details: " + ( (mangaDetails!=null) ? mangaDetails.toString():"null"));
                onChaptersDownloaded(mangaDetails);
            }
        };

        mModel.getMangaDetails().observe(this, mangaDetailsObserver);

        Observer<Map<String, ChapterDownloadInfo.Status>> statusObserver = new Observer<Map<String, ChapterDownloadInfo.Status>>() {
            @Override
            public void onChanged(@Nullable Map<String, ChapterDownloadInfo.Status> statusMap) {
                if (statusMap !=null) {
                    mStatusMap = statusMap;
                    ChaptersRecyclerViewAdapter adapter =(ChaptersRecyclerViewAdapter) ((RecyclerView) findViewById(R.id.chapter_list_view)).getAdapter();
                    if(adapter != null) {
                        adapter.updateStatus(mStatusMap);
                    }
                }
            }
        } ;

        mModel.getDownloadStatus().observe(this, statusObserver);

        Button b = findViewById(R.id.favorite_button);
        b.setEnabled(false);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onFavoriteClicked();
            }
        });
    }

    private void setFavoriteButton(Boolean isFavorite) {
       Button fav = findViewById(R.id.favorite_button);
       if(!isFavorite){
           fav.setText("Add to Favorites");
           fav.setClickable(true);
       }
       else {
           fav.setText("In Favorites");
           fav.setClickable(false);
       }
    }

    @Override
    public void onChaptersDownloaded(MangaDetails details) {
        if (details != null) {
            mMangaDetails = details;
            TextView t = findViewById(R.id.manga_title);
            t.setText(details.title);

            TrustAllPicassoFactory.getPicasso(this).load(details.titleImagePath)
                    .placeholder(R.drawable.ic_home_black_24dp)
                    .error(R.drawable.ic_notifications_black_24dp)
                    .into((ImageView) findViewById(R.id.detailsImageView));
            ChaptersRecyclerViewAdapter adapter = new ChaptersRecyclerViewAdapter(this, mMangaDetails, mStatusMap, mModel);
            ((RecyclerView) findViewById(R.id.chapter_list_view)).setAdapter(adapter);
            findViewById(R.id.favorite_button).setEnabled(true);
        }

    }

    void onFavoriteClicked() {
        mModel.addFavorite(new MangaTitle(mMangaDetails.title, mMangaDetails.url, mMangaDetails.titleImagePath));
    }


}
