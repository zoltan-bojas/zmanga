package com.avanade.bojas.zoltan.zmanga.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.avanade.bojas.zoltan.zmanga.MangaTitle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MangaBrowserViewModel extends ViewModel {
    MutableLiveData<List<MangaTitle>> mangas = new MutableLiveData<>();

    public MangaBrowserViewModel(){

    }

    public MutableLiveData<List<MangaTitle>> getMangas(String url){
        new MangaParser().execute(url);
        return mangas;
    }



    public class MangaParser extends AsyncTask<String, Void, String> {

        private List<MangaTitle> titleList;

        MangaParser() {

        };

        @Override
        protected String doInBackground(String... ulr) {
            List<MangaTitle> result = new ArrayList<MangaTitle>();
            try {
                StringBuilder b = new StringBuilder();
                Document htmlDocument = Jsoup.connect("http://www.mangahere.cc/directory/1.htm?last_chapter_time.za").get();
                Elements eList = htmlDocument.select("div.directory_list > ul > li");// .getElementsByAttribute("title");//.body().select("div.title[title]");
                b.append(eList.size());
                b.append("\n");

                for (Element e : eList) {
                    String title = e.select("div.title > a").attr("title");
                    String url = "http:" + e.select("div.title > a").attr("href");
                    String img = e.select("img").attr("src");
                    result.add(new MangaTitle(title, url, img));
                }
                titleList = result;
                return result.toString();
            }
            catch(IOException e) {
                return "";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if(titleList!=null) {
                mangas.setValue(titleList);
            }
        }

    }

}
