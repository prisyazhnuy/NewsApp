package com.prisyazhnuy.newsapp.dataKotlin.network.repos

import com.prisyazhnuy.newsapp.dataKotlin.models.News
import com.prisyazhnuy.newsapp.dataKotlin.network.api.NewsAPIService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * max.pr on 14.04.2018.
 */
interface NewsNetworkRepo {
    val api: NewsAPIService

    fun getNewsList(sources: String,
                    sortBy: String?,
                    from: String?,
                    to: String?,
                    pageSize: Int,
                    page: Int): Flowable<List<News>>
}

class NewsNetworkRepoImpl(override val api: NewsAPIService) : NewsNetworkRepo {
    override fun getNewsList(sources: String, sortBy: String?, from: String?, to: String?, pageSize: Int, page: Int): Flowable<List<News>> {
        return api.getNews(sources, from, to, sortBy, pageSize, page)
                .filter { it.status.equals("OK") }
                .map { it.articles }
                .compose {
                    it.map {
                            arrayListOf<News>().apply {
                                it.forEach {
                                    add(News(it.id, it.title, it.description, it.url, it.urlToImage))
                                }
                            }.toList()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}