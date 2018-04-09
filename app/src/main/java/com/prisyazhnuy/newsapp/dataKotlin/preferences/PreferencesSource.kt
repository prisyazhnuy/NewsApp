package com.prisyazhnuy.newsapp.dataKotlin.preferences

import android.arch.lifecycle.LiveData

/**
 * max.pr on 07.04.2018.
 */
interface PreferencesSource {

    fun setSort(type: String)

    fun getSort(): LiveData<String>
    fun getSource(): LiveData<String>
    fun getFrom(): LiveData<String>
    fun getTo(): LiveData<String>
    fun setSource(source: String)
    fun setFrom(from: String)
    fun setTo(to: String)


}