package com.prisyazhnuy.newsapp.data.remote;

import com.prisyazhnuy.newsapp.BuildConfig;

import retrofit2.Retrofit;

/**
 * max.pr on 02.03.2018.
 */

public class RestClient {

    private static NewsApiService sService;

    public static NewsApiService create() {
        if (sService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .build();

            sService = retrofit.create(NewsApiService.class);
        }
        return sService;
    }
}
