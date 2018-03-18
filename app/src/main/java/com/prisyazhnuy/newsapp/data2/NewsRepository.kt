package com.prisyazhnuy.newsapp.data2

import io.reactivex.Observable

/**
 * max.pr on 18.03.2018.
 */
class NewsRepository (private val remoteDataSource : NewsDataSource) : NewsDataSource {

    override fun getBreakingNews(): Observable<List<News>> {
        return remoteDataSource.getBreakingNews()
    }

    override fun getFavouriteNews(): List<News> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addToFavourites(news: News) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFromFavourites(news: News) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}