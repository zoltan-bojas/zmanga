package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.avanade.bojas.zoltan.zmanga.MangaTitle;
import com.avanade.bojas.zoltan.zmanga.repository.MangaTitlesRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MangaBrowserViewModel extends ViewModel {
    MediatorLiveData<List<MangaTitle>> mangas = new MediatorLiveData<>();
    MangaTitlesRepository titlesRepository;

    public MangaBrowserViewModel(MangaTitlesRepository repository){
        titlesRepository = repository;
        mangas.addSource(titlesRepository.getMangaTitles(), mangaTitles -> mangas.setValue(mangaTitles));
    }

    public MutableLiveData<List<MangaTitle>> getMangas(){
        titlesRepository.getMangaTitles();
        return mangas;
    }


}
