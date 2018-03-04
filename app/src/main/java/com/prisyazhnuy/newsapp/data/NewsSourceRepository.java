package com.prisyazhnuy.newsapp.data;

import com.prisyazhnuy.newsapp.data.pojo.SourcesResponse;
import com.prisyazhnuy.newsapp.data.remote.NewsApiService;

import io.reactivex.Observable;

/**
 * max.pr on 04.03.2018.
 */

public class NewsSourceRepository implements NewsSourceModel {

    private final NewsApiService mApiService;
    private static NewsSourceModel sInstance;

    private NewsSourceRepository(NewsApiService apiService) {
        this.mApiService = apiService;
    }

    public static NewsSourceModel getInstance(NewsApiService apiService) {
        if (sInstance == null) {
            sInstance = new NewsSourceRepository(apiService);
        }
        return sInstance;
    }

    @Override
    public Observable<SourcesResponse> getSources(String category, String language, String country) {
        return mApiService.getSources(category, language, country);
    }
}
