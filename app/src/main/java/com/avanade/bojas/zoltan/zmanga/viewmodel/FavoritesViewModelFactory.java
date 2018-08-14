package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.avanade.bojas.zoltan.zmanga.repository.FavoritesRepository;

public class FavoritesViewModelFactory implements ViewModelProvider.Factory {

    private FavoritesRepository mRepository;

    public FavoritesViewModelFactory(FavoritesRepository titlesRepository){
        mRepository = titlesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FavoritesViewModel(mRepository);
    }
}
