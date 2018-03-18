package com.prisyazhnuy.newsapp.data2.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * max.pr on 18.03.2018.
 */
interface NewsAPIService {
    @GET("everything")
    fun getNews(@Query("sources") sources: String, @Query("from") from: String?,
                @Query("to") to: String?, @Query("sortBy") sortBy: String?,
                @Query("pageSize") pageSize: Int, @Query("page") page: Int): Observable<NewsResponse>
}