package com.avanade.bojas.zoltan.zmanga;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoltan.bojas on 6/6/2018.
 */

public class MangaDetails {
    String title;
    String url;
    String titleImagePath;
    String localPath;
    List<Chapter> chapters = new ArrayList<>();

    MangaDetails(){}

    public MangaDetails(String title, String url, String titleImagePath) {
        this.title = title;
        this.url = url;
        this. titleImagePath = titleImagePath;
    }

    public void addChapter(String title, String url){
        chapters.add(new Chapter(title, url));
    }

    public class Chapter {
        String title;
        String url;
        ChapterDownloadInfo.Status status;

        Chapter(String title, String url) {
            this(title, url, ChapterDownloadInfo.Status.NOT_STARTED);
        }

        Chapter(String title, String url, ChapterDownloadInfo.Status status) {
            this.title = title;
            this. url = url;
            this.status = status;
        }
    }

}
