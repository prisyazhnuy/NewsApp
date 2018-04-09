package com.prisyazhnuy.newsapp.flow.sort

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.prisyazhnuy.newsapp.dataKotlin.preferences.PreferencesRepository
import com.prisyazhnuy.newsapp.dataKotlin.preferences.PreferencesSource

/**
 * max.pr on 05.04.2018.
 */
class SortViewModel(app: Application) : AndroidViewModel(app) {
    val preferencesSource: PreferencesSource = PreferencesRepository(app.applicationContext)

    fun getSort() = preferencesSource.getSort()

    fun setPublishedDateSort() {
        preferencesSource.setSort("publishedAt")
    }

    fun setPopularitySort() {
        preferencesSource.setSort("popularity")
    }
}