package com.prisyazhnuy.newsapp.dataKotlin

import com.prisyazhnuy.newsapp.dataKotlin.database.DataBaseCreator
import com.prisyazhnuy.newsapp.dataKotlin.database.repos.NewsDatabaseRepoImpl
import com.prisyazhnuy.newsapp.dataKotlin.database.repos.SourceDatabaseRepoImpl
import com.prisyazhnuy.newsapp.dataKotlin.models.Source
import com.prisyazhnuy.newsapp.dataKotlin.network.RestClientBuilder
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

/**
 * max.pr on 15.04.2018.
 */
interface SourceRepository {
    fun loadSourceList(category: String?,
                       language: String = "en",
                       country: String = "us"): Flowable<List<Source>>

    fun updateSource(source: Source): Flowable<Unit>
}

object SourceRepositoryImpl : SourceRepository {

    private val sourceNetworkRepo = RestClientBuilder.getSourceNetworkRepo()
    private val sourceDatabaseRepo = SourceDatabaseRepoImpl(DataBaseCreator.dataBase.sourceDao())

    override fun loadSourceList(category: String?, language: String, country: String): Flowable<List<Source>> {
        return sourceDatabaseRepo.getSources()
                .onErrorResumeNext(
                        sourceNetworkRepo.getSourceList(category, language, country)
                                .doOnEach {
                                    sourceDatabaseRepo.saveSources(it.value)
                                            .subscribe()
                                }
                )
    }

    override fun updateSource(source: Source): Flowable<Unit> {
        return sourceDatabaseRepo.update(source)
    }

}