package com.example.githubdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.common.util.DataStoreUtils
import com.example.githubdemo.ui.page.AppPage
import com.example.githubdemo.ui.theme.GitHubDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-28 21:23
 */
@AndroidEntryPoint
class TestMainActivity  : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DataStoreUtils.init(this.applicationContext)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setContent {
      val navController = rememberNavController()
      GitHubDemoTheme {
        AppPage(navController)
      }
    }
  }
}