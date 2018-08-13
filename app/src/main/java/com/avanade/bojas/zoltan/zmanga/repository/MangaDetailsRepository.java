package com.avanade.bojas.zoltan.zmanga.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.avanade.bojas.zoltan.zmanga.MangaDetails;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MangaDetailsRepository {

    String mUrl;
    MutableLiveData<MangaDetails> mDetails = new MutableLiveData<>();


    public MangaDetailsRepository(String url) {
        mUrl = url;
    }


    public MutableLiveData<MangaDetails> getDetails(){
        new DetailsParser().execute(mUrl);
        return mDetails;
    }


    class DetailsParser extends AsyncTask<String, Void, MangaDetails> {


        MangaDetails details;

        @Override
        protected MangaDetails doInBackground(String... urlParam) {
            String url = urlParam[0];
            try {
                Document htmlDocument = Jsoup.connect(url).get();

                String title = htmlDocument.select("meta[property=og:title]").attr("content");
                String titleImagePath = htmlDocument.select("img[class=img]").attr("src");
                Elements chapterElements = htmlDocument.select("div.detail_list > ul > li > span.left> a");

                 details = new MangaDetails(title, url, titleImagePath );

                for (Element e : chapterElements) {
                    details.addChapter(e.text(), "http:" + e.attr("href"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return details;
        }

        @Override
        protected void onPostExecute(MangaDetails result) {
            mDetails.setValue(result);
        }

    }


}
