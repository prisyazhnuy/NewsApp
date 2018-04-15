package com.prisyazhnuy.newsapp.dataKotlin.database.dao

import android.arch.persistence.room.*
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.NewsEntity
import io.reactivex.Flowable

/**
 * max.pr on 29.03.2018.
 */
@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun loadAllNews(): Flowable<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: NewsEntity)

    @Delete
    fun delete(news: NewsEntity)
}