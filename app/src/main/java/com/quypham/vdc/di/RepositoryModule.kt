package com.quypham.vdc.di

import com.quypham.vdc.Constants
import com.quypham.vdc.api.ApiService
import com.quypham.vdc.database.AppDatabase
import com.quypham.vdc.repo.SettingRepository
import com.quypham.vdc.repo.SettingRepositoryImp
import com.quypham.vdc.repo.WeatherRepository
import com.quypham.vdc.repo.WeatherRepositoryImp
import com.quypham.vdc.repo.setting.SettingDataSource
import com.quypham.vdc.repo.setting.SettingLocalDataSource
import com.quypham.vdc.repo.weather.WeatherDataSource
import com.quypham.vdc.repo.weather.WeatherLocalDataSource
import com.quypham.vdc.repo.weather.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    @Named(Constants.LOCAL)
    fun provideLocalUserDataSource(): WeatherDataSource {
        return WeatherLocalDataSource()
    }

    @Singleton
    @Provides
    @Named(Constants.REMOTE)
    fun provideRemoteUserDataSource(apiService: ApiService): WeatherDataSource {
        return WeatherRemoteDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(@Named(Constants.REMOTE) remoteSource: WeatherDataSource): WeatherRepository {
        return WeatherRepositoryImp(remoteSource)
    }

    @Singleton
    @Provides
    @Named(Constants.LOCAL)
    fun provideLocalSettingDataSource(appDatabase: AppDatabase): SettingDataSource {
        return SettingLocalDataSource(appDatabase)
    }

    @Singleton
    @Provides
    fun provideSettingRepository(@Named(Constants.LOCAL) localSource: SettingDataSource): SettingRepository {
        return SettingRepositoryImp(localSource)
    }
}