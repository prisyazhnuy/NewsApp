package com.prisyazhnuy.newsapp.news_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.prisyazhnuy.newsapp.R;

public class NewsListActivity extends AppCompatActivity {

    private static final String TAG = "NewsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
