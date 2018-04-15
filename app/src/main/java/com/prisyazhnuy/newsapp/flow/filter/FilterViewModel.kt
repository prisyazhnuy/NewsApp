package com.prisyazhnuy.newsapp.flow.filter

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.prisyazhnuy.newsapp.dataKotlin.SourceRepository
import com.prisyazhnuy.newsapp.dataKotlin.SourceRepositoryImpl
import com.prisyazhnuy.newsapp.dataKotlin.models.Source
import com.prisyazhnuy.newsapp.dataKotlin.preferences.PreferencesRepository
import java.text.SimpleDateFormat
import java.util.*

/**
 * max.pr on 08.04.2018.
 */
class FilterViewModel(app: Application) : AndroidViewModel(app) {

    private val prefRepository = PreferencesRepository(app.applicationContext)
    private val sourceRepo: SourceRepository = SourceRepositoryImpl

    val sourceLiveData: MutableLiveData<List<Source>> = MutableLiveData()
    val sourceErrorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    val filterLiveData = FilterLiveData().apply {
        this.addSource(prefRepository.getSource()) { it ->
            it?.let { this.source = it }

        }
        this.addSource(prefRepository.getFrom()) { it ->
            it?.let { this.from = it }

        }
        this.addSource(prefRepository.getTo()) { it ->
            it?.let { this.to = it }

        }
    }

    fun loadSources() {
        sourceRepo.loadSourceList(null)
                .subscribe({ sourceLiveData.value = it },
                        { sourceErrorLiveData.value = it })
    }

    fun setSources(sources: String) {
        prefRepository.setSource(sources)
    }

    fun setFrom(from: String) {
        prefRepository.setFrom(from)
    }

    fun setTo(to: String) {
        prefRepository.setTo(to)
    }

    fun setFromDate(year: Int, month: Int, day: Int) {
        val date = getDate(year, month, day)
        setFrom(date)
    }

    fun setToDate(year: Int, month: Int, day: Int) {
        val date = getDate(year, month, day)
        setTo(date)
    }

    private fun getDate(year: Int, month: Int, day: Int): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = GregorianCalendar(year, month, day)
        return dateFormat.format(calendar.time)
    }
}