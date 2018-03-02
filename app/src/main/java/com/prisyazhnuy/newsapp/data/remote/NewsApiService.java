package com.prisyazhnuy.newsapp.data.remote;

import com.prisyazhnuy.newsapp.data.pojo.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * max.pr on 02.03.2018.
 */

public interface NewsApiService {

    @GET("top-headlines")
    Observable<NewsResponse> getTopHeadlinesByCountry(@Query("country") String country,
                                                      @Query("category") String category,
                                                      @Query("q") String query,
                                                      @Query("pageSize") int pageSize,
                                                      @Query("page") int page);

    @GET("top-headlines")
    Observable<NewsResponse> getTopHeadlinesBySource(@Query("sources") String sources,
                                             @Query("q") String query,
                                             @Query("pageSize") int pageSize,
                                             @Query("page") int page);
}
