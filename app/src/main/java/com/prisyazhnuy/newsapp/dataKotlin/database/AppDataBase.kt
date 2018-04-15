package com.prisyazhnuy.newsapp.dataKotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.NewsEntity
import com.prisyazhnuy.newsapp.dataKotlin.database.dao.NewsDao
import com.prisyazhnuy.newsapp.dataKotlin.database.dao.SourceDao
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.SourceEntity

/**
 * max.pr on 29.03.2018.
 */
@Database(entities = arrayOf(NewsEntity::class, SourceEntity::class), version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    abstract fun sourceDao(): SourceDao

    companion object {
        const val DATABASE_NAME = "news_db"
    }
}