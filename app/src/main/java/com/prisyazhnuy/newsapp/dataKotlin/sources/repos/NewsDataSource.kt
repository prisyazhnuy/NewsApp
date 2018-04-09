package com.prisyazhnuy.newsapp.dataKotlin.sources.repos

import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import io.reactivex.Observable
import io.reactivex.Single

/**
 * max.pr on 29.03.2018.
 */
interface NewsDataSource {
    fun getNews(): Observable<List<News>>

    fun getNews(source: String,
                from: String?,
                to: String?,
                sortBy: String?,
                pageSize: Int,
                page: Int): Observable<List<News>>

    fun deleteNews(news: News)

    fun saveNews(news: News)
}