package com.prisyazhnuy.newsapp.dataKotlin.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * max.pr on 14.04.2018.
 */
@Entity(tableName = "sources")
data class SourceEntity(@PrimaryKey val id: Long?,
                        @SerializedName("id") val sourceId: String?,
                        val name: String?,
                        val isChecked: Boolean?)