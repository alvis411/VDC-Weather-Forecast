package com.quypham.vdc.api

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object ServiceGenerator {
    private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MB
    private const val CACHE_FILE_NAME = "cacheResponse"
    private val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val mRetrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    private var mRetrofit: Retrofit? = null
    private var mCache: Cache? = null
    private val mHttpClientBuilder = OkHttpClient.Builder()

    fun setBaseUrl(baseUrl: String) {
        mRetrofit = mRetrofitBuilder.baseUrl(baseUrl)
                        .client(mHttpClientBuilder.build())
                        .build()
    }

    fun setInterceptor(interceptor: Interceptor) {
        mHttpClientBuilder.interceptors().clear()
        mHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        if (!mHttpClientBuilder.interceptors().contains(interceptor)) {
            mHttpClientBuilder.addInterceptor(interceptor)
        }
        mRetrofitBuilder.client(mHttpClientBuilder.build())
    }

    fun setCache(cacheDir: File) {
        if (mCache == null) {
            val file = File(cacheDir.absolutePath, CACHE_FILE_NAME)
            if (!file.exists()) {
                file.mkdir()
            }
            mCache = Cache(file, CACHE_SIZE)
        }
        mHttpClientBuilder.cache(mCache)
    }

    fun <S> createService(serviceClass: Class<S>): S = mRetrofit!!.create(serviceClass)
}