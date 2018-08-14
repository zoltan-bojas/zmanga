package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avanade.bojas.zoltan.zmanga.repository.ChaptersRepository;

import java.io.File;
import java.util.ArrayList;

public class ChapterReaderViewModel extends ViewModel {
    private final MediatorLiveData<ArrayList<String>> pages = new MediatorLiveData<>();
    private ChaptersRepository mChaptersRepository;

    ChapterReaderViewModel(File chapterDirectory, ChaptersRepository repository) {
        mChaptersRepository = repository;
        pages.setValue(new ArrayList<String>());
        pages.addSource(mChaptersRepository.getPageLocations(), strings -> pages.setValue(strings));
        mChaptersRepository.getLocalChapter(chapterDirectory);

    }

    public MutableLiveData<ArrayList<String>> getPages(){
        return pages;
    }

}
