package com.prisyazhnuy.newsapp.data2.remote

import com.prisyazhnuy.newsapp.data2.News
import com.prisyazhnuy.newsapp.data2.NewsDataSource
import io.reactivex.Observable

/**
 * max.pr on 18.03.2018.
 */
class NewsRemoteRepository(val api: NewsAPIService) : NewsDataSource {
    override fun getBreakingNews(): Observable<List<News>> {
        return api.getNews("lenta", null, null, null, 25, 1)
                .flatMap { t -> Observable.just(t?.articles) }
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