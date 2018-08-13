package com.avanade.bojas.zoltan.zmanga;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by zoltan.bojas on 6/4/2018.
 */

@Entity(tableName = "favorites_table")
public class MangaTitle {
    @PrimaryKey
    @NonNull
    public String title;
    public String url;
    public String img_url;


    public MangaTitle(String title, String url, String img_url) {
        this.title=title;
        this.url=url;
        this.img_url=img_url;
    }


}
