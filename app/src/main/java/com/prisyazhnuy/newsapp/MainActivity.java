package com.prisyazhnuy.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.filter.FilterFragment;
import com.prisyazhnuy.newsapp.news_list.NewsListFragment;
import com.prisyazhnuy.newsapp.sort.SortFragment;

public class MainActivity extends AppCompatActivity
        implements NewsListFragment.OnClickNewsItemListener,
        SortFragment.OnSortChangedListener,
        FilterFragment.OnFilterChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemClicked(Article item) {

    }

    @Override
    public void onSortChanged() {
        reloadNews();
    }

    @Override
    public void onFilterChanged() {
        reloadNews();
    }

    private void reloadNews() {
        NewsListFragment newsListFrag = (NewsListFragment)
                getSupportFragmentManager().findFragmentById(R.id.newsListFragment);
        if (newsListFrag != null) {
            newsListFrag.getPresenter().loadNews();
        }
    }
}
