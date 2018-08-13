package com.avanade.bojas.zoltan.zmanga;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import static com.avanade.bojas.zoltan.zmanga.ChapterDownloadInfo.Status.FINISHED;
import static com.avanade.bojas.zoltan.zmanga.ChapterDownloadInfo.Status.STARTED;

@Entity(tableName = "downloads_table")
public class ChapterDownloadInfo {

    @PrimaryKey
    @NonNull
    public String localDirectory;
    @TypeConverters(StatusConverter.class)
    public Status status;
    public int maxPages;

    public ChapterDownloadInfo(String directory){
        this(directory, STARTED,0);
    }

    public ChapterDownloadInfo(String localDirectory, Status status, int maxPages){
        this.localDirectory=localDirectory;
        this.status=status;
        this.maxPages=maxPages;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public enum Status {
        NOT_STARTED(0), STARTED(1), FINISHED(2);
        int code;

        Status(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    };

    public static class StatusConverter {
        @TypeConverter
        public static ChapterDownloadInfo.Status toStatus(int status) {
            if (status == STARTED.getCode()) {
                return STARTED;
            } else if (status == FINISHED.getCode()) {
                return FINISHED;
            }
            else {
                throw new IllegalArgumentException("Could not recognize status");
            }
        }

        @TypeConverter
        public static Integer toInteger(ChapterDownloadInfo.Status status) {
            return status.getCode();
        }
    }

}
