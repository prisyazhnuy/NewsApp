package com.prisyazhnuy.newsapp.dataKotlin.sources.repos

import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import com.prisyazhnuy.newsapp.dataKotlin.sources.remote.RestClientBuilder
import io.reactivex.Observable
import io.reactivex.Single

/**
 * max.pr on 29.03.2018.
 */
object NewsRemoteSource : NewsDataSource {

    val apiService = RestClientBuilder.mNewsAPIService

    override fun getNews(): Observable<List<News>> {
        return Observable.just(emptyList())
    }

    override fun getNews(source: String, from: String?, to: String?, sortBy: String?, pageSize: Int, page: Int)
            = apiService
            .getNews(source, from, to, sortBy, pageSize, page)
            .doOnSubscribe{Log.d("TAG3", "Subscribe")}
            .doOnEach{it -> Log.d("TAG3", "data: $it")}
            .map { t -> t.articles }
    override

    fun deleteNews(news: News) {
    }

    override fun saveNews(news: News) {
    }
}