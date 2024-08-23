package com.example.githubdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.blankj.utilcode.BuildConfig
import com.example.common.data.constants.Constants
import com.example.githubdemo.ui.page.AppPage
import com.example.githubdemo.ui.theme.GitHubDemoTheme
import com.example.githubdemo.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val viewModel: MainActivityViewModel by viewModels()

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

    setIntent(null)
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