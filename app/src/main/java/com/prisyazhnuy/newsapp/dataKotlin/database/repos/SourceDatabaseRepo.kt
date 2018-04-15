package com.prisyazhnuy.newsapp.dataKotlin.database.repos

import android.util.Log
import com.prisyazhnuy.newsapp.dataKotlin.database.dao.SourceDao
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.SourceEntity
import com.prisyazhnuy.newsapp.dataKotlin.models.Source
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * max.pr on 15.04.2018.
 */
interface SourceDatabaseRepo {

    val dao: SourceDao

    fun getSources(): Flowable<List<Source>>

    fun saveSources(sources: List<Source>): Flowable<Unit>

    fun update(source: Source): Flowable<Unit>

    fun deleteAll(): Flowable<Unit>
}

class SourceDatabaseRepoImpl(override val dao: SourceDao) : SourceDatabaseRepo {

    override fun deleteAll(): Flowable<Unit> {
        return Flowable.fromCallable { dao.deleteAll() }
    }

    override fun getSources(): Flowable<List<Source>> {
        return dao.loadSourcesList()
                .compose {
                    it.map {
                        arrayListOf<Source>().apply {
                            it.forEach {
                                add(Source(it.id, it.sourceId, it.name, it.isChecked))
                            }
                        }.toList()
                    }
                }
                .doOnEach {
                    if (it.value.isEmpty()) throw Exception()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveSources(sources: List<Source>): Flowable<Unit> {
        return Flowable.fromIterable(sources)
                .compose {
                    it.map {
                        SourceEntity(it.id, it.sourceId, it.name, it.isChecked)
                    }
                }
                .map {
                    dao.insert(it)
                }
                .doOnError { Log.e(SourceDatabaseRepo::class.java.simpleName, "insert error", it) }
                .map { Unit }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    override fun update(source: Source): Flowable<Unit> {
        return Flowable.just(source)
                .compose {
                    it.map {
                        SourceEntity(it.id, it.sourceId, it.name, it.isChecked)
                    }
                }
                .map { dao.update(it) }
                .map { Unit }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}