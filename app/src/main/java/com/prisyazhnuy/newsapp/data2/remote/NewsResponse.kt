package com.prisyazhnuy.newsapp.data2.remote

import com.prisyazhnuy.newsapp.data2.News

/**
 * max.pr on 18.03.2018.
 */
class NewsResponse(val status : String, val totalResults : Int, val articles : List<News>) {
}