package com.prisyazhnuy.newsapp.dataKotlin

import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import io.reactivex.Observable
import io.reactivex.Single

/**
 * max.pr on 18.03.2018.
 */
interface NewsDataSource {
    fun getBreakingNews(source: String, sortBy: String? = "publishedAt", from: String?, to: String?, page: Int = 1) : Observable<List<News>>

    fun getFavouriteNews() : Observable<List<News>>

    fun addToFavourites(news : News)

    fun removeFromFavourites(news: News)


}