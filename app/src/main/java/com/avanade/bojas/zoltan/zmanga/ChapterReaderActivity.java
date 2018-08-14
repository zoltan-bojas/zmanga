package com.avanade.bojas.zoltan.zmanga;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.avanade.bojas.zoltan.zmanga.repository.ChaptersRepository;
import com.avanade.bojas.zoltan.zmanga.viewmodel.ChapterReaderViewModel;
import com.avanade.bojas.zoltan.zmanga.viewmodel.ChapterReaderViewModelFactory;

import java.io.File;
import java.util.ArrayList;

public class ChapterReaderActivity extends AppCompatActivity {

    static String DOWNLOAD_DIRECTORY_EXTRA = "com.avanade.bojas.zoltan.zmanga.DOWNLOAD_DIRECTORY";
    ChapterReaderViewModel mModel;
    ChaptersRepository mChaptersRepository;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_chapter_reader);
        mChaptersRepository = new ChaptersRepository();
        Intent intent = this.getIntent();
        String dir = intent.getStringExtra(DOWNLOAD_DIRECTORY_EXTRA);
        File targetDirectory = this.getDir(dir, Context.MODE_PRIVATE);
        PagesRecyclerViewAdapter adapter = new PagesRecyclerViewAdapter(mContext, new ArrayList<>());
        ((RecyclerView) findViewById(R.id.chapter_pages_list_view)).setAdapter(adapter);

        mModel = ViewModelProviders
                .of(this, new ChapterReaderViewModelFactory(targetDirectory, mChaptersRepository))
                .get(ChapterReaderViewModel.class);



        // Create the observer which updates the UI.
        final Observer<ArrayList<String>> pageObserver = newFileList -> {
            // Update the UI
            PagesRecyclerViewAdapter adapter1 =
                    (PagesRecyclerViewAdapter) ((RecyclerView) findViewById(R.id.chapter_pages_list_view)).getAdapter();
            adapter1.pageLocations = newFileList;
            adapter1.notifyDataSetChanged();
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mModel.getPages().observe(this, pageObserver);

    }




}
