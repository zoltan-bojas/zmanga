package com.avanade.bojas.zoltan.zmanga;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avanade.bojas.zoltan.zmanga.viewmodel.MangaTitleDetailsViewModel;

import java.io.File;
import java.util.Map;

/**
 * Created by zoltan.bojas on 6/6/2018.
 */

public class ChaptersRecyclerViewAdapter extends RecyclerView.Adapter<ChaptersRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final MangaDetails mDetails;
    private Map<String, ChapterDownloadInfo.Status> mStatusMap;
    private final MangaTitleDetailsViewModel mModel;

    public ChaptersRecyclerViewAdapter(Context context,
                                       MangaDetails details,
                                       Map<String, ChapterDownloadInfo.Status> statusMap,
                                       MangaTitleDetailsViewModel model) {
        mContext = context;
        mDetails = details;
        mStatusMap = statusMap;
        mModel = model;
    }

    public void updateStatus(Map<String, ChapterDownloadInfo.Status> status) {
        mStatusMap = status;
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_item, parent, false);
        return new ChaptersRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.chapterName = mDetails.chapters.get(position).title;
        holder.mChapterNameview.setText(mDetails.chapters.get(position).title);
        holder.url = mDetails.chapters.get(position).url;
        holder.mDownloadButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File targetDiretory = mContext.getDir(holder.chapterName, Context.MODE_PRIVATE);
                        mModel.downloadChapter(holder.url, targetDiretory);
                    }
                }
        );

        ChapterDownloadInfo.Status s = mStatusMap.get(mDetails.chapters.get(position).url);

        if (s==null) {
            Log.e("status code", "status code in adapter: null");
            holder.mDownloadButton.setText("Download");
            holder.mDownloadButton.setClickable(true);
        }
        else if(s == ChapterDownloadInfo.Status.STARTED) {
            Log.e("status code", "status code in adapter: " + s.getCode());
            holder.mDownloadButton.setText("In Progress");
            holder.mDownloadButton.setClickable(false);
        } else if (s == ChapterDownloadInfo.Status.FINISHED){
            Log.e("status code", "status code in adapter: " + s.getCode());
            holder.mDownloadButton.setText("Done");
            holder.mDownloadButton.setClickable(false);
        }




        holder.mView.setOnClickListener( (v)-> {
                    Intent readerIntent = new Intent(mContext, ChapterReaderActivity.class);
                    readerIntent.putExtra(ChapterReaderActivity.DOWNLOAD_DIRECTORY_EXTRA, holder.chapterName);
                    mContext.startActivity(readerIntent);
                }

        );

    }

    @Override
    public int getItemCount() {
        return mDetails.chapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final TextView mChapterNameview;
        public final Button mDownloadButton;
        String chapterName;
        String url;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mChapterNameview = (TextView) view.findViewById(R.id.chapter_name);
            mDownloadButton = (Button) view.findViewById(R.id.downloadButton);

        }

    }
}
