package com.prisyazhnuy.newsapp.data;

import com.prisyazhnuy.newsapp.data.pojo.SourcesResponse;

import io.reactivex.Observable;

/**
 * max.pr on 04.03.2018.
 */

public interface NewsSourceModel {

    Observable<SourcesResponse> getSources(String category, String language, String country);


}
