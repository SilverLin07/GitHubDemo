package com.example.githubdemo

import com.example.common.base.BaseApp
import com.example.common.util.DataStoreUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GitHubApp: BaseApp() {
  override fun onCreate() {
    super.onCreate()

    // 数据存储
    DataStoreUtils.init(this)
  }
}