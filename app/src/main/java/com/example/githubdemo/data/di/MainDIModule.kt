package com.example.githubdemo.data.di

import com.example.common.data.network.ApiManager
import com.example.githubdemo.data.network.MainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:46
 */
@Module
@InstallIn(SingletonComponent::class)
class MainDIModule {
  @Singleton
  @Provides
  fun provideFindService(): MainService = ApiManager.retrofit.create(MainService::class.java)
}