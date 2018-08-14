package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avanade.bojas.zoltan.zmanga.ChapterDownloadInfo;
import com.avanade.bojas.zoltan.zmanga.MangaDetails;
import com.avanade.bojas.zoltan.zmanga.MangaTitle;
import com.avanade.bojas.zoltan.zmanga.repository.DownloadsRepository;
import com.avanade.bojas.zoltan.zmanga.repository.FavoritesRepository;
import com.avanade.bojas.zoltan.zmanga.repository.ChaptersRepository;
import com.avanade.bojas.zoltan.zmanga.repository.MangaDetailsRepository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MangaTitleDetailsViewModel extends ViewModel {
    public final MediatorLiveData<MangaDetails> mangaDetails = new MediatorLiveData();
    public final MediatorLiveData<Map<String,ChapterDownloadInfo.Status>> downloadStatus = new MediatorLiveData();
    public final MediatorLiveData<Boolean> isFavorite = new MediatorLiveData();

    private MangaDetailsRepository detailsRepository;
    private DownloadsRepository downloadsRepository;
    private ChaptersRepository chaptersRepopsitory;
    private FavoritesRepository favoritesRepository;

    private String mFavorite;

    public MangaTitleDetailsViewModel(MangaDetailsRepository detailsRepository,
                                      DownloadsRepository downloadsRepository,
                                      ChaptersRepository chaptersRepopsitory,
                                      FavoritesRepository favoritesRepository){
        this.detailsRepository = detailsRepository;
        this.downloadsRepository = downloadsRepository;
        this.chaptersRepopsitory = chaptersRepopsitory;
        this.favoritesRepository = favoritesRepository;

        isFavorite.addSource(favoritesRepository.getFavorites(), new Observer<List<MangaTitle>>() {
            @Override
            public void onChanged(@Nullable List<MangaTitle> titles) {
                //TODO: replace with direct query
              Boolean isFav = false;
              for(int i=0; i<titles.size(); i++) {
                  if(titles.get(i).url.equals(mFavorite)) {
                      isFav = true;
                  }
              }
                isFavorite.setValue(isFav);



            }
        });

        mangaDetails.addSource(detailsRepository.getDetails(), result -> mangaDetails.setValue(result));

        downloadStatus.addSource(downloadsRepository.getDownloadInfo(), chapterDownloadInfos -> {
            Map<String,ChapterDownloadInfo.Status> results = new HashMap<>();

            Log.e("download info", "download info returnd to model: "
                    + chapterDownloadInfos.get(0).localDirectory
                    +", status code: "
                    + chapterDownloadInfos.get(0).status.getCode());
            Log.e("download info", "download info size: "+ chapterDownloadInfos.size());
            for(int i= 0; i<chapterDownloadInfos.size();i++){
                results.put(chapterDownloadInfos.get(i).localDirectory,chapterDownloadInfos.get(i).status);
            }
            downloadStatus.setValue(results);
        });

    }

    public void downloadChapter(String url, File targetDirectory) {
        downloadsRepository.addDownoadInfo(new ChapterDownloadInfo(url));
        chaptersRepopsitory.downloadChapter(url, targetDirectory);
        downloadsRepository.addDownoadInfo(new ChapterDownloadInfo(url,ChapterDownloadInfo.Status.FINISHED,0));
    }

    public void addFavorite(MangaTitle newFavorite) {
        mFavorite = newFavorite.url;
        favoritesRepository.addFavorite(newFavorite);
    }

    public MutableLiveData<Boolean> isFavorite(String newFavorite) {
       mFavorite = newFavorite;
       favoritesRepository.getFavorites();
        return isFavorite;
    }

    public MediatorLiveData<MangaDetails> getMangaDetails() {
        return mangaDetails;
    }

    public MediatorLiveData<Map<String, ChapterDownloadInfo.Status>> getDownloadStatus() {
        return downloadStatus;
    }
}
