package com.prisyazhnuy.newsapp.dataKotlin.network.api

import com.prisyazhnuy.newsapp.dataKotlin.network.beans.SourceResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import com.prisyazhnuy.newsapp.dataKotlin.network.NetworkContract.API_VERSION
import retrofit2.http.Query

/**
 * max.pr on 14.04.2018.
 */
interface SourceApi {

    @GET("/$API_VERSION/sources")
    fun getSources(@Query("category") category: String?,
                   @Query("language") language: String?,
                   @Query("country") country: String?): Flowable<SourceResponse>
}