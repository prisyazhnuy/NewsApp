package com.prisyazhnuy.newsapp.news_list;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * max.pr on 02.03.2018.
 */

public class NewsRecycleViewAdapter extends RecyclerView.Adapter<NewsRecycleViewAdapter.ViewHolder> {

    private List<Article> mValues;
    private List<Article> mFavourites;
    private final NewsInteractionListener mListener;

    public NewsRecycleViewAdapter(NewsInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void setFavourites(List<Article> articles) {
        this.mFavourites = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTvTitle.setText(mValues.get(position).getTitle());
        holder.mTvDescription.setText(mValues.get(position).getDescription());
        String path = holder.mItem.getUrlToImage();
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("http")) {
                Picasso.with(holder.mView.getContext())
                        .load(path)
                        .resize(50, 50)
                        .centerCrop()
                        .into(holder.mIvIcon);
            } else {
                Picasso.with(holder.mView.getContext())
                        .load(new File(path))
                        .resize(50, 50)
                        .centerCrop()
                        .into(holder.mIvIcon);
            }
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClicked(holder.mItem);
                }
            }
        });
        holder.mCbFavourite.setOnCheckedChangeListener(null);
        if (mFavourites != null) {
            holder.mCbFavourite.setChecked(mFavourites.contains(holder.mItem));
        }
        holder.mCbFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (mListener != null) {
                    mListener.onItemChecked(holder.mItem, isChecked);
                }
            }
        });
        holder.mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onShareClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void clear() {
        final int size = mValues.size();
        mValues.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<Article> articles) {
        mValues.addAll(articles);
        notifyDataSetChanged();
    }

    public void removeItem(String url) {
        for (int i = 0; i < mValues.size(); i++) {
            if (mValues.get(i).getUrl().equals(url)) {
                mValues.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Article mItem;
        public final View mView;
        public final TextView mTvTitle;
        public final TextView mTvDescription;
        public final ImageView mIvIcon;
        public final ImageButton mBtnShare;
        public final CheckBox mCbFavourite;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTvTitle = view.findViewById(R.id.tvTitle);
            mTvDescription = view.findViewById(R.id.tvDescription);
            mIvIcon = view.findViewById(R.id.ivIcon);
            mBtnShare = view.findViewById(R.id.btnShare);
            mCbFavourite = view.findViewById(R.id.cbIsFavourite);
        }
    }
}
