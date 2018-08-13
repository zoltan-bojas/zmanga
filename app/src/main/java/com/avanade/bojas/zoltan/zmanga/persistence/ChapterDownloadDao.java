package com.avanade.bojas.zoltan.zmanga.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.avanade.bojas.zoltan.zmanga.ChapterDownloadInfo;
import com.avanade.bojas.zoltan.zmanga.MangaTitle;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ChapterDownloadDao {

    @Query("select * from downloads_table")
    List<ChapterDownloadInfo> getChapters();

    @Insert(onConflict = REPLACE)
    void addDownload(ChapterDownloadInfo donloadInfo);

}