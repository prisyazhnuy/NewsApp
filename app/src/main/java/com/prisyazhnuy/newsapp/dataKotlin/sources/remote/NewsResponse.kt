package com.prisyazhnuy.newsapp.dataKotlin.sources.remote

import com.prisyazhnuy.newsapp.dataKotlin.entity.News


/**
 * max.pr on 18.03.2018.
 */
class NewsResponse(val status : String, val totalResults : Int, val articles : List<News>) {
}