package com.prisyazhnuy.newsapp.data;

import com.prisyazhnuy.newsapp.data.pojo.NewsResponse;
import com.prisyazhnuy.newsapp.data.remote.NewsApiService;

import io.reactivex.Observable;

/**
 * max.pr on 02.03.2018.
 */

public class NewsRepository implements NewsModel {

    private final NewsApiService mApiService;
    private static NewsRepository sInstance;

    private NewsRepository(NewsApiService apiService) {
        this.mApiService = apiService;
    }

    public static NewsModel getInstance(NewsApiService apiService) {
        if (sInstance == null) {
            sInstance = new NewsRepository(apiService);
        }
        return sInstance;
    }

    @Override
    public Observable<NewsResponse> getAllNews(int pageSize, int page) {
        return mApiService.getAllNews(pageSize, page);
    }

    @Override
    public Observable<NewsResponse> getNewsByFilter(String source, String from, String to, String sortBy, int pageSize, int page) {
        return mApiService.getNewsByFilter(source, from, to, sortBy, pageSize, page);
    }
}
