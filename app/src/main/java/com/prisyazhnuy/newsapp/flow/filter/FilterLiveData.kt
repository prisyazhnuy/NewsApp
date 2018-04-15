package com.prisyazhnuy.newsapp.flow.filter

import android.arch.lifecycle.MediatorLiveData
import com.prisyazhnuy.newsapp.dataKotlin.SourceRepositoryImpl

/**
 * max.pr on 08.04.2018.
 */
class FilterLiveData : MediatorLiveData<Filter>() {

    var category: String? = null
        set(value) {
            field = value
            SourceRepositoryImpl.loadSourceList(value)
                    .subscribe()
        }


    var source: String? = null
        set(value) {
            value?.let {
                field = value
                this@FilterLiveData.value = Filter(value, from, to)
            }
        }

    var from: String? = null
        set(value) {
            value?.let {
                field = value
                this@FilterLiveData.value = source?.let { it1 -> Filter(it1, value, to) }
            }
        }

    var to: String? = null
        set(value) {
            value?.let {
                field = value
                this@FilterLiveData.value = source?.let { it1 -> Filter(it1, from, value) }
            }
        }


}