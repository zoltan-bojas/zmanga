package com.avanade.bojas.zoltan.zmanga.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.avanade.bojas.zoltan.zmanga.MangaTitle;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by zoltan.bojas on 6/6/2018.
 */
@Dao
public interface FavoriteDao {

    @Query("select * from favorites_table")
    List<MangaTitle> getFavorites();

    @Insert(onConflict = IGNORE)
    void addFavorite(MangaTitle manga);
}
