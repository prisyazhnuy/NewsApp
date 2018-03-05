package com.prisyazhnuy.newsapp.news_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * max.pr on 02.03.2018.
 */

public class NewsRecycleViewAdapter extends RecyclerView.Adapter<NewsRecycleViewAdapter.ViewHolder> {

    private List<Article> mValues;
    private final NewsListFragment.OnClickNewsItemListener mListener;

    public NewsRecycleViewAdapter(List<Article> items, NewsListFragment.OnClickNewsItemListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
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
        Picasso.with(holder.mView.getContext())
                .load(holder.mItem.getUrlToImage())
                .resize(50, 50)
                .centerCrop()
                .into(holder.mIvIcon);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClicked(holder.mItem);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Article mItem;
        public final View mView;
        public final TextView mTvTitle;
        public final TextView mTvDescription;
        public final ImageView mIvIcon;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTvTitle = (TextView) view.findViewById(R.id.tvTitle);
            mTvDescription = (TextView) view.findViewById(R.id.tvDescription);
            mIvIcon = view.findViewById(R.id.ivIcon);
        }
    }
}
