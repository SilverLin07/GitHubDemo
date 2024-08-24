package com.example.githubdemo

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.Utils.Consumer
import com.example.common.base.BaseApp
import com.example.common.ext.findActivity
import com.example.common.util.DataStoreUtils
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class GitHubApp: BaseApp() {
  override fun onCreate() {
    super.onCreate()

    DataStoreUtils.init(this)
  }
}