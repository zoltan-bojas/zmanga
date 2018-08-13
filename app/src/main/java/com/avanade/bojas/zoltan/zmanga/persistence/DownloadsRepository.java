package com.avanade.bojas.zoltan.zmanga.persistence;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.avanade.bojas.zoltan.zmanga.ChapterDownloadInfo;

import java.util.List;

public class DownloadsRepository {

    MutableLiveData<List<ChapterDownloadInfo>> downloadedChapters = new MutableLiveData();


    public DownloadsRepository(MangaDatabase db) {
        mChapterDownloadDao = db.downloadsDao();
    }

    private ChapterDownloadDao mChapterDownloadDao;

    public void addDownoadInfo(ChapterDownloadInfo downloadInfo) {
        Log.e("download info", "adding download info with path: " + downloadInfo.localDirectory);
        new DownloadsRepository.DownloadInfoInserter(mChapterDownloadDao).execute(downloadInfo);

    }

    public MutableLiveData<List<ChapterDownloadInfo>> getDownloadInfo() {
        new DownloadsRepository.DownloadInfoRetriever(mChapterDownloadDao).execute();
        return downloadedChapters;
    }


    private class DownloadInfoInserter extends AsyncTask<ChapterDownloadInfo,Void,Void> {

        ChapterDownloadDao mDownloadDao;

        DownloadInfoInserter(ChapterDownloadDao dao) {
            mDownloadDao =dao;
        }

        @Override
        protected Void doInBackground(final ChapterDownloadInfo... params) {
            mDownloadDao.addDownload(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            new DownloadInfoRetriever(mDownloadDao).execute();
        }


    }

    private class DownloadInfoRetriever extends AsyncTask<Void, Void, List<ChapterDownloadInfo>> {

        ChapterDownloadDao mChapterDownloadDao;

        DownloadInfoRetriever(ChapterDownloadDao dao) {
            mChapterDownloadDao = dao;

        }

        @Override
        protected List<ChapterDownloadInfo> doInBackground(Void... voids) {
            return mChapterDownloadDao.getChapters();
        }

        @Override
        protected void onPostExecute(List<ChapterDownloadInfo> result){
            downloadedChapters.setValue(result);
        }
    }

}
