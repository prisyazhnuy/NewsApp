package com.prisyazhnuy.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.favourite.FavouriteListActivity;
import com.prisyazhnuy.newsapp.filter.FilterFragment;
import com.prisyazhnuy.newsapp.news_list.NewsInteractionListener;
import com.prisyazhnuy.newsapp.news_list.NewsListContract;
import com.prisyazhnuy.newsapp.news_list.NewsListFragment;
import com.prisyazhnuy.newsapp.sort.SortFragment;

public class MainActivity extends AppCompatActivity
        implements NewsInteractionListener,
        SortFragment.OnSortChangedListener,
        FilterFragment.OnFilterChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favourites:
                startActivity(new Intent(this, FavouriteListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(Article item) {
        Intent browser = new Intent(this, BrowserActivity.class);
        browser.putExtra("url", item.getUrl());
        startActivity(browser);
    }

    @Override
    public void onItemChecked(Article item) {

    }

    @Override
    public void onShareClicked(Article item) {

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
            NewsListContract.NewsListPresenter presenter = newsListFrag.getPresenter();
            if (presenter != null) {
                presenter.loadBreakNews();
            }
        }
    }
}
