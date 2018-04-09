package com.prisyazhnuy.newsapp.flow.newsList

import android.arch.lifecycle.MediatorLiveData
import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.NewsRepository
import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import com.prisyazhnuy.newsapp.flow.filter.Filter
import io.reactivex.disposables.CompositeDisposable

/**
 * max.pr on 01.04.2018.
 */
class NewsLiveData : MediatorLiveData<Pair<List<News>?, Throwable?>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    var page: Int? = null
        set(value) {
            value?.let {
                filter?.let { it1 ->
                    compositeDisposable.add(NewsRepository
                            .getBreakingNews(it1.source, sortBy, it1.from, it1.to, it)
                            .subscribe { data -> this@NewsLiveData.value = Pair(data, null) })
                }
            }
        }

    var isFavourite: Boolean? = null
        set(value) {
            value?.let {
                field = value
                if (value) {
                    compositeDisposable.add(NewsRepository
                            .getFavouriteNews()
                            .doOnNext { data -> Log.d("LIveData", "data: $data") }
                            .doOnSubscribe { Log.d("LIveData", "Subscribe") }
                            .doOnDispose { Log.d("LIveData", "Dispose") }
                            .subscribe { data -> this@NewsLiveData.postValue(Pair(data, null)) })
                } else {
                    compositeDisposable.add(filter?.let { it1 ->
                        NewsRepository.getBreakingNews(it1.source, sortBy, it1.from, it1.to)
                                .subscribe { data -> this@NewsLiveData.postValue(Pair(data, null)) }
                    })
                }
            }
        }

    var sortBy: String? = null
        set(value) {
            value?.let {
                field = value
                compositeDisposable.add(filter?.let { it1 ->
                    NewsRepository
                            .getBreakingNews(it1.source, it, from = it1.from, to = it1.to)
                            .subscribe { data -> this@NewsLiveData.postValue(Pair(data, null)) }

                })
            }
        }

    var filter: Filter? = null
        set(value) {
            value?.let {
                field = value
                compositeDisposable.add(
                        NewsRepository
                                .getBreakingNews(it.source, sortBy, it.from, it.to)
                                .subscribe { data -> this@NewsLiveData.postValue(Pair(data, null)) })

            }
        }

    override fun onInactive() {
        super.onInactive()
        if (compositeDisposable.isDisposed.not()) {
            compositeDisposable.clear()
        }
    }
}