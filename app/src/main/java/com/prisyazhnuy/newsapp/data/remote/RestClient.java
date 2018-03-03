package com.prisyazhnuy.newsapp.data.remote;

import com.prisyazhnuy.newsapp.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * max.pr on 02.03.2018.
 */

public class RestClient {

    private static final int CONNECTION_TIMEOUT = 10;
    private static NewsApiService sService;

    public static NewsApiService create() {
        if (sService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    return chain.proceed(request.newBuilder()
                            .addHeader("X-Api-Key", BuildConfig.API_KEY).build());
                }
            });
            httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            httpClient.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            httpClient.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            sService = retrofit.create(NewsApiService.class);
        }
        return sService;
    }
}
