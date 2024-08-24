package com.example.githubdemo

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.blankj.utilcode.util.LanguageUtils
import com.example.common.data.constants.Constants
import com.example.common.ext.findActivity
import com.example.common.util.DataStoreUtils
import com.example.githubdemo.ui.page.AppPage
import com.example.githubdemo.ui.theme.GitHubDemoTheme
import com.example.githubdemo.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val viewModel: MainActivityViewModel by viewModels()

  override fun attachBaseContext(newBase: Context?) {
    val language = DataStoreUtils.readStringData(DataStoreUtils.SETTING_LANGUAGE, "")
    if (language.isNotBlank()) {
      super.attachBaseContext(setLocale(newBase, language))
    } else {
      super.attachBaseContext(newBase)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setContent {
      val navController = rememberNavController()
      GitHubDemoTheme {
        AppPage(navController)
      }
    }

    if (intent != null && intent.data != null) {
      val uri = intent.data
      if (uri.toString().startsWith(Constants.REDIRECT_URL)) {
        val tokenCode = uri?.getQueryParameter("code") ?: ""
        viewModel.auth(tokenCode, Constants.GITHUB_CLIENT_ID, Constants.GITHUB_CLIENT_SECRET, Constants.REDIRECT_URL)
      }
    }
  }

  private fun setLocale(context: Context?, language: String): Context? {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = Configuration()
    config.setLocale(locale)

    return context?.createConfigurationContext(config)
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)

    if (intent != null && intent.data != null) {
      val uri = intent.data
      if (uri.toString().startsWith(Constants.REDIRECT_URL)) {
        val tokenCode = uri?.getQueryParameter("code") ?: ""
        viewModel.auth(tokenCode, Constants.GITHUB_CLIENT_ID, Constants.GITHUB_CLIENT_SECRET, Constants.REDIRECT_URL)
      }
    }

    setIntent(null)
  }
}