package com.prisyazhnuy.newsapp.dataKotlin

import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.database.DataBaseCreator
import com.prisyazhnuy.newsapp.dataKotlin.database.repos.NewsDatabaseRepoImpl
import com.prisyazhnuy.newsapp.dataKotlin.models.News
import com.prisyazhnuy.newsapp.dataKotlin.network.RestClientBuilder
import io.reactivex.Flowable

/**
 * max.pr on 18.03.2018.
 */
interface NewsRepository {
    fun getBreakingNews(source: String, sortBy: String?, from: String?, to: String?, page: Int = 1): Flowable<List<News>>

    fun getFavouriteNews(): Flowable<List<News>>

    fun addToFavourites(news: News)

    fun removeFromFavourites(news: News)
}


object NewsRepositoryImpl : NewsRepository {

    private val newsNetworkRepo = RestClientBuilder.getNewsNetworkRepo()
    private val newsDatabaseRepo = NewsDatabaseRepoImpl(DataBaseCreator.dataBase.newsDao())

    override fun getBreakingNews(source: String, sortBy: String?, from: String?, to: String?, page: Int): Flowable<List<News>> {
        return newsNetworkRepo.getNewsList(source, from, to, sortBy, 25, page)
    }

    override fun getFavouriteNews(): Flowable<List<News>> {
        return newsDatabaseRepo.getNewsList()
                .doOnNext { data -> Log.d("NewsRepository", "data: $data") }
                .compose {
                    it.map {
                        arrayListOf<News>().apply {
                            it.forEach {
                                add(News(it.id, it.title, it.description, it.url, it.urlToImage))
                            }
                        }.toList()
                    }
                }
    }

    override fun addToFavourites(news: News) {
        newsDatabaseRepo.saveNews(news)
    }

    override fun removeFromFavourites(news: News) {
        newsDatabaseRepo.deleteNews(news)
    }
}