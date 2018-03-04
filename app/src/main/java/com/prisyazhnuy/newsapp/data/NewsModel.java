package com.prisyazhnuy.newsapp.data;

import com.prisyazhnuy.newsapp.data.pojo.NewsResponse;
import com.prisyazhnuy.newsapp.data.pojo.SourcesResponse;

import io.reactivex.Observable;

/**
 * max.pr on 02.03.2018.
 */

public interface NewsModel {

    Observable<NewsResponse> getAllNews(int pageSize, int page);

    Observable<NewsResponse> getNewsByFilter(String source, String from,
                                             String to, String sortBy,
                                             int pageSize, int page);

}
