package com.quypham.vdc.di

import android.content.Context
import androidx.room.Room
import com.quypham.vdc.Constants
import com.quypham.vdc.api.ApiService
import com.quypham.vdc.api.ServiceGenerator
import com.quypham.vdc.database.AppDatabase
import com.quypham.vdc.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constants.APP_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        val cacheInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            if (NetworkUtil.hasNetworkConnection(context))
            //Get cache from last 5 mins in case internet is available
                requestBuilder.header("Cache-Control", "public, max-age=" + (5 * 60)).build()
            else
            //Get cache from 2 days in case no internet is available
                requestBuilder.header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()

            chain.proceed(requestBuilder.build())
        }

        ServiceGenerator.setCache(context.cacheDir)
        ServiceGenerator.setInterceptor(cacheInterceptor)
        ServiceGenerator.setBaseUrl(Constants.BASE_URL)
        return ServiceGenerator.createService(ApiService::class.java)
    }

}