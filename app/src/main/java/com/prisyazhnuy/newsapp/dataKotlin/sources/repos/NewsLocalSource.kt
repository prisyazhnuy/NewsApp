package com.prisyazhnuy.newsapp.dataKotlin.sources.repos

import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import com.prisyazhnuy.newsapp.dataKotlin.sources.db.DataBaseCreator
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * max.pr on 29.03.2018.
 */
object NewsLocalSource : NewsDataSource {

    val newsDAO = DataBaseCreator.dataBase.newsDAO()

    override fun getNews(): Observable<List<News>>
            = newsDAO
            .loadAllNews()
            .doOnEach{Log.d("NewsLocalSource", "data: $it")}
            .doOnNext{if (it.isEmpty()) Observable.error<Throwable>(Throwable("Empty list"))}
            .toObservable()

    override fun getNews(source: String, from: String?, to: String?, sortBy: String?, pageSize: Int, page: Int): Observable<List<News>> {
        return Observable.just(emptyList())
    }

    override fun deleteNews(news: News) {
        newsDAO.delete(news)
    }

    override fun saveNews(news: News) {
        Observable.just(news)
                .doOnNext { newsDAO.insert(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}