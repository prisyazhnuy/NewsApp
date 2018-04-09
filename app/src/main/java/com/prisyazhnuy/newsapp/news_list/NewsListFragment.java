package com.prisyazhnuy.newsapp.news_list;

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
import com.prisyazhnuy.newsapp.data.pojo.Article;

import java.util.List;

public class NewsListFragment extends MvpFragment<NewsListContract.NewsListView, NewsListContract.NewsListPresenter>
        implements NewsListContract.NewsListView, NewsInteractionListener {

    private static final String TAG = "NewsListFragment";
    private static final int SAVE_STORAGE_CODE = 100;
    private static final int REMOVE_STORAGE_CODE = 101;
    private RecyclerView mRecyclerView;
    private NewsRecycleViewAdapter mNewsAdapter;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Article mTempArticle;

    public NewsListFragment() {
    }

    @NonNull
    @Override
    public NewsListContract.NewsListPresenter createPresenter() {
        return null;//new NewsListPresenter(new NewsRemoteRepository(RestClientBuilder.INSTANCE.getMNewsAPIService()));
//        return new NewsListPresenterImpl(NewsRepository.getInstance(RestClient.create()),
//                DataRepository.getInstance(getContext()),
//                NewsDAORealm.getInstance(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()));
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
    public void onResume() {
        super.onResume();
        getPresenter().loadFavourites();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
        }
        Context context = mRecyclerView.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = recyclerView.getLayoutManager().getItemCount();
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    getPresenter().loadNextNews();
                }
            }
        });
        mNewsAdapter = new NewsRecycleViewAdapter( this);
        mRecyclerView.setAdapter(mNewsAdapter);
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
    public void showNews(List<Article> articles) {
        mNewsAdapter.addAll(articles);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
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
    public void delete(String url) {

    }

    @Override
    public void addFavourites(List<Article> articles) {
        mNewsAdapter.setFavourites(articles);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(Article item) {
        Intent browser = new Intent(getContext(), BrowserActivity.class);
        browser.putExtra("url", item.getUrl());
        startActivity(browser);
    }

    @Override
    public void onItemChecked(Article item, boolean isChecked) {
        if (isChecked) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                mTempArticle = item;
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_STORAGE_CODE);
            } else {
                getPresenter().saveNews(item);
            }
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                mTempArticle = item;
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REMOVE_STORAGE_CODE);
            } else {
                getPresenter().delete(item.getUrl());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SAVE_STORAGE_CODE: {
                getPresenter().saveNews(mTempArticle);
                break;
            }
            case REMOVE_STORAGE_CODE: {
                getPresenter().delete(mTempArticle.getUrl());
                break;
            }
        }
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
