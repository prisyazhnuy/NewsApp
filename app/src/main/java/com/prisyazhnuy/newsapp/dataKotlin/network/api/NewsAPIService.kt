package com.prisyazhnuy.newsapp.dataKotlin.network.api

import com.prisyazhnuy.newsapp.dataKotlin.network.beans.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.prisyazhnuy.newsapp.dataKotlin.network.NetworkContract.API_VERSION
import io.reactivex.Flowable

/**
 * max.pr on 18.03.2018.
 */
interface NewsAPIService {
    @GET("/$API_VERSION/everything")
    fun getNews(@Query("sources") sources: String, @Query("from") from: String?,
                @Query("to") to: String?, @Query("sortBy") sortBy: String?,
                @Query("pageSize") pageSize: Int, @Query("page") page: Int): Flowable<NewsResponse>
}