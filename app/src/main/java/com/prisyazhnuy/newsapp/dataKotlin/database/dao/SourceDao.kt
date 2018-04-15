package com.prisyazhnuy.newsapp.dataKotlin.database.dao

import android.arch.persistence.room.*
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.SourceEntity
import io.reactivex.Flowable

/**
 * max.pr on 15.04.2018.
 */

@Dao
interface SourceDao {

    @Query("SELECT * FROM sources")
    fun loadSourcesList(): Flowable<List<SourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(source: SourceEntity)

    @Query("DELETE FROM sources")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(source: SourceEntity)
}