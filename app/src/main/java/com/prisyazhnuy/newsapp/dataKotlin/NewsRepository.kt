package com.prisyazhnuy.newsapp.dataKotlin

import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import com.prisyazhnuy.newsapp.dataKotlin.sources.repos.NewsLocalSource
import com.prisyazhnuy.newsapp.dataKotlin.sources.repos.NewsRemoteSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * max.pr on 18.03.2018.
 */
object NewsRepository : NewsDataSource {

    override fun getBreakingNews(source: String, sortBy: String?, from: String?, to: String?, page: Int): Observable<List<News>> {
        return NewsRemoteSource.getNews(source, from, to, sortBy, 25,  page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFavouriteNews(): Observable<List<News>> {
        return NewsLocalSource.getNews()
                .doOnNext{ data -> Log.d("NewsRepository", "data: $data") }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addToFavourites(news: News) {
        NewsLocalSource.saveNews(news)
    }

    override fun removeFromFavourites(news: News) {
        NewsLocalSource.deleteNews(news)
    }
}