package com.prisyazhnuy.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.news_list.NewsListFragment;

public class MainActivity extends AppCompatActivity implements NewsListFragment.OnClickNewsItemListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemClicked(Article item) {

    }
}
