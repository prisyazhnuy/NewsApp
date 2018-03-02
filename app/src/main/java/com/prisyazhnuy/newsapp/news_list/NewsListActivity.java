package com.prisyazhnuy.newsapp.news_list;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.NewsRepository;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.data.remote.RestClient;

import java.util.List;

public class NewsListActivity extends MvpActivity<NewsListContract.NewsListView, NewsListContract.NewsListPresenter>
        implements NewsListContract.NewsListView {

    private static final String TAG = "NewsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onLoad();
    }

    @NonNull
    @Override
    public NewsListContract.NewsListPresenter createPresenter() {
        return new NewsListPresenterImpl(NewsRepository.getInstance(RestClient.create()));
    }

    @Override
    public void showNews(List<Article> articles) {
        Log.d(TAG, "News: " + articles.toString());
    }

    @Override
    public void showError() {
        Log.d(TAG, "Network error");
    }
}
