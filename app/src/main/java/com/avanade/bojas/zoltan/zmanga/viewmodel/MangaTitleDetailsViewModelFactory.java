package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.avanade.bojas.zoltan.zmanga.persistence.MangaDatabase;
import com.avanade.bojas.zoltan.zmanga.repository.ChaptersRepository;
import com.avanade.bojas.zoltan.zmanga.repository.DownloadsRepository;
import com.avanade.bojas.zoltan.zmanga.repository.FavoritesRepository;
import com.avanade.bojas.zoltan.zmanga.repository.MangaDetailsRepository;

public class MangaTitleDetailsViewModelFactory implements ViewModelProvider.Factory {
    Context context;
    String url;

    public MangaTitleDetailsViewModelFactory(Context context, String url){
        this.context = context;
        this.url = url;

    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MangaDatabase db = MangaDatabase.getDatabase(context);
        return (T)                 new MangaTitleDetailsViewModel(new MangaDetailsRepository(url),
                new DownloadsRepository(db),
                new ChaptersRepository(),
                new FavoritesRepository(db));
    }
}
