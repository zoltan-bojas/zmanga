package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.avanade.bojas.zoltan.zmanga.repository.MangaTitlesRepository;

public class MangaBrowserViewModelFactory implements ViewModelProvider.Factory {

    private MangaTitlesRepository mTitlesRepository;

    public MangaBrowserViewModelFactory(MangaTitlesRepository titlesRepository){
        mTitlesRepository = titlesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MangaBrowserViewModel(mTitlesRepository);
    }
}
