package com.prisyazhnuy.newsapp.data2.remote

import com.prisyazhnuy.newsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * max.pr on 18.03.2018.
 */
object RestClientBuilder {
    private val timeout = 10L
    var mNewsAPIService: NewsAPIService? = null

    init {
        build()
    }

    fun build(): NewsAPIService? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor { chain ->
            val request = chain.request()
            chain.proceed(request.newBuilder().addHeader("X-Api-Key", BuildConfig.API_KEY).build())
        }
        httpClient.connectTimeout(timeout, TimeUnit.SECONDS)
        httpClient.readTimeout(timeout, TimeUnit.SECONDS)
        httpClient.writeTimeout(timeout, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        mNewsAPIService = retrofit.create(NewsAPIService::class.java)
        return mNewsAPIService
    }

}