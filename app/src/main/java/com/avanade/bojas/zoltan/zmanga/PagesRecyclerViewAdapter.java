package com.avanade.bojas.zoltan.zmanga;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class PagesRecyclerViewAdapter extends RecyclerView.Adapter<PagesRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> pageLocations;
    Context mContext;

    PagesRecyclerViewAdapter(Context context, ArrayList<String> pageLocations) {
        this.pageLocations = pageLocations;
        this.mContext = context;
    }


    @Override
    public PagesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_item, parent, false);
        return  new PagesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PagesRecyclerViewAdapter.ViewHolder holder, int position) {

        Picasso picasso = TrustAllPicassoFactory.getPicasso(mContext);

        picasso.with(mContext)
                .load(new File(pageLocations.get(position)))
                .placeholder(R.drawable.ic_home_black_24dp)
                .error(R.drawable.ic_notifications_black_24dp)
                .into(holder.mPageImage);


    }

    @Override
    public int getItemCount() {
        return pageLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mPageImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mPageImage = (ImageView) itemView.findViewById(R.id.page_item_image);

        }
    }

}