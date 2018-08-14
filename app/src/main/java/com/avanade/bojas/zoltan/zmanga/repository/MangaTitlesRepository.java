package com.avanade.bojas.zoltan.zmanga.repository;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.avanade.bojas.zoltan.zmanga.MangaTitle;
import com.avanade.bojas.zoltan.zmanga.MangaTitlesListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MangaTitlesRepository {
    private MutableLiveData<List<MangaTitle>> titleList ;
    private String mUrl;

    public MangaTitlesRepository(String url) {
        mUrl = url;
    }

    public LiveData<List<MangaTitle>>  getMangaTitles(){
        if (titleList == null) {
            titleList = new MutableLiveData<>();
            titleList.setValue(new ArrayList<>());
        }
        new MangaParser().execute(mUrl);
        return titleList;

    }

    private class MangaParser extends AsyncTask<String, Void, List<MangaTitle>> {

        @Override
        protected List<MangaTitle> doInBackground(String... url) {
            List<MangaTitle> result = new ArrayList<>();
            try {
                StringBuilder b = new StringBuilder();
                Document htmlDocument = Jsoup.connect(url[0]).get();
                Elements eList = htmlDocument.select("div.directory_list > ul > li");
                b.append(eList.size());
                b.append("\n");

                for (Element e : eList) {
                    String title = e.select("div.title > a").attr("title");
                    String titleUrl = "http:" + e.select("div.title > a").attr("href");
                    String img = e.select("img").attr("src");
                    result.add(new MangaTitle(title, titleUrl, img));
                }
                return result;
            }
            catch(IOException e) {
                return result;
            }

        }

        @Override
        protected void onPostExecute(List<MangaTitle> result) {
            titleList.setValue(result);
        }

    }


}
