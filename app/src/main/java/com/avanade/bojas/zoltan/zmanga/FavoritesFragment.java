package com.avanade.bojas.zoltan.zmanga;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.avanade.bojas.zoltan.zmanga.persistence.FavoritesRepository;
import com.avanade.bojas.zoltan.zmanga.persistence.MangaDatabase;
import com.avanade.bojas.zoltan.zmanga.viewmodel.MangaBrowserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoltan.bojas on 6/7/2018.
 */

public class FavoritesFragment extends TitlesFragment {

    FavoritesRepository repository;
    MangaBrowserViewModel mModel;
    @Override
    public void setUpAdapter(RecyclerView recyclerView) {

        repository = new FavoritesRepository(MangaDatabase.getDatabase(this.getActivity()));
        //TODO: rewrite this using view model (MVVM)
         repository.getFavorites().observe(this, new Observer<List<MangaTitle>>() {
             @Override
             public void onChanged(@Nullable List<MangaTitle> mangaTitles) {
                 TitleItemRecyclerViewAdapter adapter = new TitleItemRecyclerViewAdapter(getContext().getApplicationContext(), mangaTitles, mListener);
                 recyclerView.setAdapter(adapter);
             }
         });



    }

}
