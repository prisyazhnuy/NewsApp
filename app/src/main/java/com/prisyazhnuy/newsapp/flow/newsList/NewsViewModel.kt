package com.prisyazhnuy.newsapp.flow.newsList

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.prisyazhnuy.newsapp.dataKotlin.NewsRepository
import com.prisyazhnuy.newsapp.dataKotlin.NewsRepositoryImpl
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.NewsEntity
import com.prisyazhnuy.newsapp.dataKotlin.models.News
import com.prisyazhnuy.newsapp.flow.filter.Filter

/**
 * max.pr on 01.04.2018.
 */
class NewsViewModel(app: Application) : AndroidViewModel(app) {

    private var page = 1

    private val pageLiveData = MutableLiveData<Int>()
    private val favouriteLiveData = MutableLiveData<Boolean>()
    private val sortLiveData = MutableLiveData<String>()
    private val filterLiveData = MutableLiveData<Filter>()

    val resultLiveData = NewsLiveData().apply {
        this.addSource(pageLiveData) { it -> it?.let { this.page = it }}
        this.addSource(favouriteLiveData) {it -> it?.let { this.isFavourite = it }}
        this.addSource(sortLiveData) {it -> it?.let { this.sortBy = it }}
        this.addSource(filterLiveData) { it -> it?.let { this.filter = it }}
    }

    val isLoadingLiveData = MediatorLiveData<Boolean>().apply {
        this.addSource(resultLiveData) { this.value = false }
    }

    val throwableLiveData = MediatorLiveData<Throwable?>().apply {
        this.addSource(resultLiveData) { it?.second?.let { this.value = it } }
    }

    val newsLiveData = MediatorLiveData<List<News>>().apply {
        this.addSource(resultLiveData) {it?.first?.let { this.value = it }}
    }

    fun increasePage() {
        page++
        pageLiveData.value = page
        isLoadingLiveData.value = true
    }

    fun setFavourite(news: News, isFavourite: Boolean) {
        if (isFavourite) {
            NewsRepositoryImpl.addToFavourites(news)
        } else {
            NewsRepositoryImpl.removeFromFavourites(news)
        }
    }

    fun loadFavourite() {
        favouriteLiveData.value = true
        isLoadingLiveData.value = true
    }

    fun setSort(sortBy: String?) {
//        favouriteLiveData.value = false
        isLoadingLiveData.value = true
        page = 1
//        pageLiveData.value = page
        sortLiveData.value = sortBy
    }

    fun setFilter(filter: Filter?) {
        page = 1
        filterLiveData.value = filter
        isLoadingLiveData.value = true
    }
}