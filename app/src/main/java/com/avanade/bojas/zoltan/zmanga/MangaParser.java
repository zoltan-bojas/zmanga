package com.avanade.bojas.zoltan.zmanga;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoltan.bojas on 5/25/2018.
 */

public class MangaParser extends AsyncTask<String, Void, String> {

    private MangaTitlesListener viewToUpdate;
    private List<MangaTitle> titleList;

    MangaParser(MangaTitlesListener viewToUpdate) {
        this.viewToUpdate = viewToUpdate;
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
            viewToUpdate.onMangaTitlesDownoaded(titleList);
        }
    }

}
