package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.avanade.bojas.zoltan.zmanga.MangaTitle;
import com.avanade.bojas.zoltan.zmanga.repository.FavoritesRepository;

import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private MediatorLiveData<List<MangaTitle>> mangas = new MediatorLiveData<>();
    private FavoritesRepository mFavoritesRepository;
    FavoritesViewModel(FavoritesRepository favoritesRepository) {
        mFavoritesRepository = favoritesRepository;
        mangas.addSource(favoritesRepository.getFavorites(), favorites -> mangas.setValue(favorites));
    }
    public MediatorLiveData<List<MangaTitle>> getFavorites() {
        mFavoritesRepository.getFavorites();
        return mangas;
    }
}
