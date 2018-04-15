package com.prisyazhnuy.newsapp.dataKotlin.network.repos

import com.prisyazhnuy.newsapp.dataKotlin.models.Source
import com.prisyazhnuy.newsapp.dataKotlin.network.api.SourceApi
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * max.pr on 14.04.2018.
 */
interface SourceNetworkRepo {
    val api: SourceApi

    fun getSourceList(category: String?,
                      language: String?,
                      country: String?): Flowable<List<Source>>
}

class SourceNetworkRepoImpl(override val api: SourceApi) : SourceNetworkRepo {
    override fun getSourceList(category: String?, language: String?, country: String?): Flowable<List<Source>> {
        return api.getSources(category, language, country)
                .map { it.sources }
                .compose {
                    it.map {
                        arrayListOf<Source>().apply {
                            it.forEach {
                                add(Source(null, it.id, it.name, false))
                            }
                        }.toList()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}