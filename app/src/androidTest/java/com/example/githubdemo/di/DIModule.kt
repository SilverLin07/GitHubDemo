package com.example.githubdemo.di

import com.example.common.data.di.CommonDIModule
import com.example.common.data.network.ApiManager
import com.example.common.data.network.CommonService
import com.example.common.util.eventbus.EventBus
import com.example.githubdemo.data.di.MainDIModule
import com.example.githubdemo.data.network.MainService
import com.example.githubdemo.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject
import javax.inject.Singleton


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-28 22:10
 */
@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [MainDIModule::class])
object TestModule {
  @Singleton
  @Provides
  fun provideFindService(): MainService = ApiManager.retrofit.create(MainService::class.java)
}

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [CommonDIModule::class])
object TestCommonModule {
  @Singleton
  @Provides
  fun provideCommonService(): CommonService = ApiManager.retrofit.create(CommonService::class.java)

  @Singleton
  @Provides
  fun provideEventBus(): EventBus = EventBus()
}