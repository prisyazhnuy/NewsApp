package com.prisyazhnuy.newsapp.favourite;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.prisyazhnuy.newsapp.BrowserActivity;
import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.local.db.NewsDAORealm;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.news_list.NewsInteractionListener;
import com.prisyazhnuy.newsapp.news_list.NewsListContract;
import com.prisyazhnuy.newsapp.news_list.NewsRecycleViewAdapter;

import java.util.List;

public class FavouriteListFragment extends MvpFragment<NewsListContract.NewsListView,
        NewsListContract.NewsListPresenter> implements NewsListContract.NewsListView, NewsInteractionListener {

    private static final int EXTERNAL_STORAGE_CODE = 100;
    private RecyclerView mRecyclerView;
    private NewsRecycleViewAdapter mNewsAdapter;

    public FavouriteListFragment() {
    }

    @NonNull
    @Override
    public NewsListContract.NewsListPresenter createPresenter() {
        return new FavouriteListPresenterImpl(NewsDAORealm.getInstance(getContext().getCacheDir().getPath()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
        }
        Context context = mRecyclerView.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mNewsAdapter = new NewsRecycleViewAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_CODE);
        } else {
            getPresenter().loadBreakNews();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_CODE: {
                getPresenter().loadBreakNews();
                break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void delete(String url) {
        mNewsAdapter.removeItem(url);
    }

    @Override
    public void addFavourites(List<Article> articles) {

    }

    @Override
    public void showNews(List<Article> articles) {
        mNewsAdapter.setFavourites(articles);
        mNewsAdapter.addAll(articles);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyList() {
        Toast.makeText(getContext(), getString(R.string.no_news), Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearList() {
        mNewsAdapter.clear();
    }

    @Override
    public void onItemClicked(Article item) {
        Intent browser = new Intent(getContext(), BrowserActivity.class);
        browser.putExtra("url", item.getUrl());
        startActivity(browser);
    }

    @Override
    public void onItemChecked(Article item, boolean isChecked) {
        getPresenter().delete(item.getUrl());
    }

    @Override
    public void onShareClicked(Article item) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(item.getUrl()))
                .build();
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }
}
