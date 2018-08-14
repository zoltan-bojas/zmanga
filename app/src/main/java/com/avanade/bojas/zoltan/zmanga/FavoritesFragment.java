package com.avanade.bojas.zoltan.zmanga;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.avanade.bojas.zoltan.zmanga.repository.FavoritesRepository;
import com.avanade.bojas.zoltan.zmanga.persistence.MangaDatabase;
import com.avanade.bojas.zoltan.zmanga.viewmodel.FavoritesViewModel;
import com.avanade.bojas.zoltan.zmanga.viewmodel.FavoritesViewModelFactory;
import com.avanade.bojas.zoltan.zmanga.viewmodel.MangaBrowserViewModel;

import java.util.ArrayList;

/**
 * Created by zoltan.bojas on 6/7/2018.
 */

public class FavoritesFragment extends TitlesFragment {

    FavoritesRepository repository;
    FavoritesViewModel mModel;
    @Override
    public void setUpAdapter(RecyclerView recyclerView) {
        TitleItemRecyclerViewAdapter adapter = new TitleItemRecyclerViewAdapter(getContext().getApplicationContext(), new ArrayList<>(), mListener);
        recyclerView.setAdapter(adapter);
        repository = new FavoritesRepository(MangaDatabase.getDatabase(this.getActivity()));

        mModel = ViewModelProviders
                .of(this.getActivity(), new FavoritesViewModelFactory(repository))
                .get(FavoritesViewModel.class);
        mModel.getFavorites().observe(this,titles -> adapter.onMangaTitlesDownoaded(titles));
    }




}
