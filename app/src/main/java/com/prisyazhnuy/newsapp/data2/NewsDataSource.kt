package com.prisyazhnuy.newsapp.data2

import io.reactivex.Observable

/**
 * max.pr on 18.03.2018.
 */
interface NewsDataSource {
    fun getBreakingNews() : Observable<List<News>>

    fun getFavouriteNews() : List<News>

    fun addToFavourites(news : News)

    fun removeFromFavourites(news: News)


}