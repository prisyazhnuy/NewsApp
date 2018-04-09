package com.prisyazhnuy.newsapp

import android.app.Application
import com.prisyazhnuy.newsapp.dataKotlin.sources.db.DataBaseCreator
import io.realm.Realm

/**
 * max.pr on 18.03.2018.
 */
class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        DataBaseCreator.createDB(this)
    }
}