package com.example.common.data.di

import com.example.common.data.network.ApiManager
import com.example.common.data.network.CommonService
import com.example.common.util.eventbus.EventBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonDIModule {
  @Singleton
  @Provides
  fun provideCommonService(): CommonService = ApiManager.retrofit.create(CommonService::class.java)

  @Singleton
  @Provides
  fun provideEventBus(): EventBus = EventBus()
}