package com.prisyazhnuy.newsapp.dataKotlin.network.beans

import com.prisyazhnuy.newsapp.dataKotlin.database.entity.NewsEntity


/**
 * max.pr on 18.03.2018.
 */
class NewsResponse(val status : String, val totalResults : Int, val articles : List<NewsEntity>)