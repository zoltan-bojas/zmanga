package com.avanade.bojas.zoltan.zmanga.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.avanade.bojas.zoltan.zmanga.MangaTitle;
import com.avanade.bojas.zoltan.zmanga.MangaTitlesListener;
import com.avanade.bojas.zoltan.zmanga.persistence.FavoriteDao;
import com.avanade.bojas.zoltan.zmanga.persistence.MangaDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoltan.bojas on 6/7/2018.
 */

public class FavoritesRepository {
    MutableLiveData<List<MangaTitle>> mFavorites = new MutableLiveData<>();

    public FavoritesRepository(MangaDatabase db) {
        mFavoriteDao = db.titleDao();
    }

    private FavoriteDao mFavoriteDao;

    public void addFavorite(MangaTitle title) {
        new DownloadInfoInserter(mFavoriteDao).execute(title);

    }

    public MutableLiveData<List<MangaTitle>> getFavorites() {
        new FavoritRetriever(mFavoriteDao).execute();
        return mFavorites;
    }


    private class DownloadInfoInserter extends AsyncTask<MangaTitle,Void,Void> {

        FavoriteDao mFavoriteDao;

        DownloadInfoInserter(FavoriteDao dao) {
            mFavoriteDao=dao;
        }

        @Override
        protected Void doInBackground(final MangaTitle... params) {
            mFavoriteDao.addFavorite(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            new FavoritRetriever(mFavoriteDao).execute();
        }

    }

    private class FavoritRetriever extends AsyncTask<Void, Void, List<MangaTitle>> {

        FavoriteDao mFavoriteDao;
        MangaTitlesListener mListener;


        FavoritRetriever(FavoriteDao dao) {
            mFavoriteDao = dao;
        }

        @Override
        protected List<MangaTitle> doInBackground(Void... voids) {
            return mFavoriteDao.getFavorites();
        }

        @Override
        protected void onPostExecute(List<MangaTitle> result){
            mFavorites.setValue(result);
        }
    }
}
