package com.prisyazhnuy.newsapp.dataKotlin.database.repos

import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.database.dao.NewsDao
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.NewsEntity
import com.prisyazhnuy.newsapp.dataKotlin.models.News
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * max.pr on 15.04.2018.
 */
interface NewsDatabaseRepo {
    val dao: NewsDao

    fun getNewsList(): Flowable<List<News>>

    fun saveNews(news: News): Flowable<Unit>

    fun deleteNews(news: News): Flowable<Unit>
}

class NewsDatabaseRepoImpl(override val dao: NewsDao) : NewsDatabaseRepo {

    override fun getNewsList(): Flowable<List<News>> {
        return dao.loadAllNews()
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

    override fun saveNews(news: News): Flowable<Unit> {
        return Flowable.just(news)
                .compose {
                    it.map {
                        NewsEntity(id = it.id, author = null, title = it.title, description = it.description,
                                url = it.url, urlToImage = it.urlToImage, publishedAt = null)
                    }
                }
                .map { dao.insert(it) }
                .map { Unit }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteNews(news: News): Flowable<Unit> {
        return Flowable.just(news)
                .compose {
                    it.map {
                        NewsEntity(id = it.id, author = null, title = it.title, description = it.description,
                                url = it.url, urlToImage = it.urlToImage, publishedAt = null)
                    }
                }
                .map { dao.delete(it) }
                .map { Unit }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
