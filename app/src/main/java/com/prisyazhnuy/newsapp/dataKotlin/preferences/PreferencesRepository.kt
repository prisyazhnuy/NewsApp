package com.prisyazhnuy.newsapp.dataKotlin.preferences

import android.content.Context
import android.content.SharedPreferences
import com.prisyazhnuy.newsapp.R
import com.prisyazhnuy.newsapp.flow.sort.stringLiveData

/**
 * max.pr on 07.04.2018.
 */
class PreferencesRepository(val context: Context) : PreferencesSource {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    private val SORT_BY = "sort_by"
    private val SOURCES = "sources"
    private val FROM = "from"
    private val TO = "to"

    override fun getSource()= sharedPreferences.stringLiveData(SOURCES, "")

    override fun getFrom() = sharedPreferences.stringLiveData(FROM, "")

    override fun getTo() = sharedPreferences.stringLiveData(TO, "")

    override fun setSource(source: String) {
        sharedPreferences.edit().putString(SOURCES, source).apply()
    }

    override fun setFrom(from: String) {
        sharedPreferences.edit().putString(FROM, from).apply()
    }

    override fun setTo(to: String) {
        sharedPreferences.edit().putString(TO, to).apply()
    }

    override fun setSort(type: String) {
        sharedPreferences.edit().putString(SORT_BY, type).apply()
    }

    override fun getSort() = sharedPreferences.stringLiveData(SORT_BY, "publishedAt")


}