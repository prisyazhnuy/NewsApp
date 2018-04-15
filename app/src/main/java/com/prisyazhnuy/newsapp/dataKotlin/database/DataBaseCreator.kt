package com.prisyazhnuy.newsapp.dataKotlin.database

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean

/**
 * max.pr on 29.03.2018.
 */
object DataBaseCreator {
    val isDataBaseCreated = MutableLiveData<Boolean>()

    lateinit var dataBase: AppDataBase

    private val mInitializing = AtomicBoolean(true)

    fun createDB(context: Context) {
        if (mInitializing.compareAndSet(true, false)) {
            isDataBaseCreated.value = false
            Completable.fromAction {
                dataBase = Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.DATABASE_NAME).build()
            }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ isDataBaseCreated.value = true }, { it.printStackTrace() })
        }
    }
}