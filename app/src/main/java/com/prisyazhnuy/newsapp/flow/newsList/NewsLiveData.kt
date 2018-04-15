package com.prisyazhnuy.newsapp.flow.newsList

import android.arch.lifecycle.MediatorLiveData
import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.NewsRepositoryImpl
import com.prisyazhnuy.newsapp.dataKotlin.models.News
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
                    compositeDisposable.add(NewsRepositoryImpl
                            .getBreakingNews(it1.source, sortBy, it1.from, it1.to, it)
                            .subscribe({ data -> this@NewsLiveData.postValue(Pair(data, null)) },
                                    { t -> this@NewsLiveData.postValue(Pair(null, t)) }))
                }
            }
        }

    var isFavourite: Boolean? = null
        set(value) {
            value?.let {
                field = value
                if (value) {
                    compositeDisposable.add(NewsRepositoryImpl
                            .getFavouriteNews()
                            .doOnNext { data -> Log.d("LIveData", "data: $data") }
                            .doOnSubscribe { Log.d("LIveData", "Subscribe") }
                            .subscribe({ data -> this@NewsLiveData.postValue(Pair(data, null)) },
                                    { t -> this@NewsLiveData.postValue(Pair(null, t)) }))
                } else {
                    compositeDisposable.add(filter?.let { it1 ->
                        NewsRepositoryImpl.getBreakingNews(it1.source, sortBy, it1.from, it1.to)
                                .subscribe({ data -> this@NewsLiveData.postValue(Pair(data, null)) },
                                        { t -> this@NewsLiveData.postValue(Pair(null, t)) })
                    })
                }
            }
        }

    var sortBy: String? = null
        set(value) {
            if (value.isNullOrEmpty().not()) {
                field = value
                compositeDisposable.add(filter?.let { filter ->
                    NewsRepositoryImpl
                            .getBreakingNews(filter.source, value, from = filter.from, to = filter.to)
                            .subscribe({ data -> this@NewsLiveData.postValue(Pair(data, null)) },
                                    { t -> this@NewsLiveData.postValue(Pair(null, t)) })

                })
            }
        }

    var filter: Filter? = null
        set(value) {
            value?.let {
                field = value
                compositeDisposable.add(
                        NewsRepositoryImpl
                                .getBreakingNews(it.source, sortBy, it.from, it.to)
                                .subscribe({ data -> this@NewsLiveData.postValue(Pair(data, null)) },
                                        { t -> this@NewsLiveData.postValue(Pair(null, t)) }))

            }
        }

    override fun onInactive() {
        super.onInactive()
        if (compositeDisposable.isDisposed.not()) {
            compositeDisposable.clear()
        }
    }
}