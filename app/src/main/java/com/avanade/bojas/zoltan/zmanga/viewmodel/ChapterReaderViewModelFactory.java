package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.avanade.bojas.zoltan.zmanga.repository.ChaptersRepository;

import java.io.File;

public class ChapterReaderViewModelFactory implements ViewModelProvider.Factory {
    File mTargetDirectory;
    ChaptersRepository mChaptersRepository;

    public ChapterReaderViewModelFactory(File targetDirectory, ChaptersRepository repo) {
        mTargetDirectory = targetDirectory;
        mChaptersRepository = repo;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChapterReaderViewModel(mTargetDirectory, mChaptersRepository);
    }
}
