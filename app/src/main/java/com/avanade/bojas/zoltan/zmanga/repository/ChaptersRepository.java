package com.avanade.bojas.zoltan.zmanga.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import java.io.File;
import java.util.ArrayList;

public class ChaptersRepository {

    MutableLiveData<ArrayList<String>> pageLocations;

    public MutableLiveData<ArrayList<String>> getPageLocations(){
        if(pageLocations==null) {
            pageLocations = new MutableLiveData<ArrayList<String>>();
            pageLocations.setValue(new ArrayList<String>());

        }
        return pageLocations;

    }

    public ChaptersRepository(){
    }

    public void downloadChapter(String url , File targetDirectory) {
        new ChapterDownloader().execute(new Pair<>(url, targetDirectory));

    }

    public void getLocalChapter(File chapterDir) {
        new GetFileLocationsFromLocalStorage().execute(chapterDir);
    }

    private class GetFileLocationsFromLocalStorage extends AsyncTask<File, Void, ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(File... params) {
            ArrayList<String> paths = new ArrayList<>();

            File directory = params[0];
            File[] files = directory.listFiles();
            if (files == null) {return paths;} //Nothing Downloaded yet.
            for (int i = 0; i < files.length; i++)
            {
                paths.add(files[i].getAbsolutePath());
            }
            return paths;
        }

        @Override
        protected void onPostExecute(ArrayList<String> paths) {
            pageLocations.setValue(paths);
            return;
        }
    };
}
