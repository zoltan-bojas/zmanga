package com.avanade.bojas.zoltan.zmanga.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.avanade.bojas.zoltan.zmanga.ChapterDownloadInfo;
import com.avanade.bojas.zoltan.zmanga.MangaTitle;

/**
 * Created by zoltan.bojas on 6/6/2018.
 */

@Database(entities={MangaTitle.class, ChapterDownloadInfo.class}, version = 1)
public abstract class MangaDatabase extends RoomDatabase {
    public abstract FavoriteDao titleDao();
    public abstract ChapterDownloadDao downloadsDao();

    private static MangaDatabase INSTANCE;


    public static MangaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MangaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MangaDatabase.class, "manga_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
