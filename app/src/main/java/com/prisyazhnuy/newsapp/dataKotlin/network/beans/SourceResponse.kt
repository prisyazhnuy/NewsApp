package com.prisyazhnuy.newsapp.dataKotlin.network.beans

/**
 * max.pr on 14.04.2018.
 */
data class SourceResponse(override val status: String,
                          val sources: List<SourceBean>) : BaseResponse