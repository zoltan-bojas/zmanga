package com.avanade.bojas.zoltan.zmanga;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import com.avanade.bojas.zoltan.zmanga.TitlesFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MangaTitle} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TitleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<TitleItemRecyclerViewAdapter.ViewHolder> implements MangaTitlesListener {

    private final List<MangaTitle> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public TitleItemRecyclerViewAdapter(Context context, List<MangaTitle> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public void onMangaTitlesDownoaded(List<MangaTitle> mangaTitleList) {
        mValues.addAll(mangaTitleList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).url); //mValues.get(position).url


        Picasso picasso = TrustAllPicassoFactory.getPicasso(mContext);
        picasso.with(mContext).load(mValues.get(position).img_url).placeholder(R.drawable.ic_home_black_24dp).error(R.drawable.ic_notifications_black_24dp).into(holder.mImg);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mContentView;
        MangaTitle mItem;
        final ImageView mImg;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImg = (ImageView) view.findViewById(R.id.manga_image);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
