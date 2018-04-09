package com.prisyazhnuy.newsapp.dataKotlin.sources.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import com.prisyazhnuy.newsapp.dataKotlin.sources.db.dao.NewsDAO

/**
 * max.pr on 29.03.2018.
 */
@Database(entities = arrayOf(News::class), version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun newsDAO(): NewsDAO

    companion object {
        const val DATABASE_NAME = "news_db"
    }
}