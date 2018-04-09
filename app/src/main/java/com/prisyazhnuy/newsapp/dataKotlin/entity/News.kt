package com.prisyazhnuy.newsapp.dataKotlin.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * max.pr on 29.03.2018.
 */
@Entity(tableName = "news")
data class News(
        @PrimaryKey var id: Long?,
        @ColumnInfo(name = "author") var author: String?,
        @ColumnInfo(name = "title") var title: String?,
        @ColumnInfo(name = "description") var description: String?,
        @ColumnInfo(name = "url") var url: String?,
        @ColumnInfo(name = "urlToImage") var urlToImage: String?,
        @ColumnInfo(name = "publishedAt") var publishedAt: String?)