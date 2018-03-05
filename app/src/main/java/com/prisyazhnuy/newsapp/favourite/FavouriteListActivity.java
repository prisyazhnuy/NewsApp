package com.prisyazhnuy.newsapp.favourite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.news_list.NewsInteractionListener;

public class FavouriteListActivity extends AppCompatActivity implements NewsInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
    }

    @Override
    public void onItemClicked(Article item) {

    }

    @Override
    public void onItemChecked(Article item) {

    }

    @Override
    public void onShareClicked(Article item) {

    }
}
