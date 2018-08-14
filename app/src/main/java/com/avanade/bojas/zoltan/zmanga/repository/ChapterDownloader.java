package com.avanade.bojas.zoltan.zmanga.repository;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


public class ChapterDownloader extends AsyncTask<Pair<String,File>, Void, Void> {

    ChapterDownloader() {

    }

    private void download(ArrayList<String> pages, File directory){

        for(int i =0; i<pages.size(); i++) {
            try {
                URL url = new URL(pages.get(i));
                //url.openConnection();
                InputStream inputStream = url.openStream();
                File targetFile = new File(directory.getPath(), "page" + String.valueOf(i)+".jpg" );
                String pathFull = targetFile.getAbsolutePath();
                OutputStream outStream = new FileOutputStream(targetFile);

                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outStream.close();

                Log.e("download", "page " + i +"downloaded to " + pathFull );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Void doInBackground(Pair<String,File>... params) {
        String url = params[0].first;
        File targetDirectory = params[0].second;
        ArrayList<String> pages = getPages(url);
        download(pages, targetDirectory);
        return null;
    }

    private ArrayList<String> getPages(String startUrl) {
        ArrayList<String> pages = new ArrayList<>();
        try {

            String url = startUrl;
            while(!url.contains("javascript:void(0);")){
                Document htmlDocument = Jsoup.connect(url).get();
                //TODO: add error handling, if chapter is not present the select can be empty (eg licensed)
                String page = htmlDocument.select("section.read_img > a > img").get(1).attr("src");
                pages.add(page);
                url = "http:" + htmlDocument.select("section.read_img > a").attr("href");
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        return pages;
    }
}
