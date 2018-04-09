package com.prisyazhnuy.newsapp.dataKotlin.sources.db.dao

import android.arch.persistence.room.*
import com.prisyazhnuy.newsapp.dataKotlin.entity.News
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * max.pr on 29.03.2018.
 */
@Dao
interface NewsDAO {
    @Query("SELECT * FROM news")
    fun loadAllNews(): Flowable<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)

    @Delete
    fun delete(news: News)
}