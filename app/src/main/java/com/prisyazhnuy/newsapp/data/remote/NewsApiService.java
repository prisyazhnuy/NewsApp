package com.prisyazhnuy.newsapp.data.remote;

import com.prisyazhnuy.newsapp.data.pojo.NewsResponse;
import com.prisyazhnuy.newsapp.data.pojo.SourcesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * max.pr on 02.03.2018.
 */

public interface NewsApiService {

    @GET("everything")
    Observable<NewsResponse> getAllNews(@Query("pageSize") int pageSize,
                                        @Query("page") int page);

    @GET("everything")
    Observable<NewsResponse> getNewsSortBy(@Query("sortBy") String sortBy,
                                           @Query("pageSize") int pageSize,
                                           @Query("page") int page);

    @GET("everything")
    Observable<NewsResponse> getNewsByFilter(@Query("sources") String sources,
                                             @Query("from") String from,
                                             @Query("to") String to,
                                             @Query("sortBy") String sortBy,
                                             @Query("pageSize") int pageSize,
                                             @Query("page") int page);

    @GET("sources")
    Observable<SourcesResponse> getSources(@Query("category") String category,
                                           @Query("language") String language,
                                           @Query("country") String country);
}
