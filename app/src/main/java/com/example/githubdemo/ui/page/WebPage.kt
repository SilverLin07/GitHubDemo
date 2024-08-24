package com.example.githubdemo.ui.page

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.ColumnPage
import com.example.githubdemo.ui.widget.LoadingProgress
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:12
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebPage(url: String, showTitle: Boolean = true, title: String = "", navController: NavController) {
  val state = rememberWebViewState(url = url)
  val webClient = remember {
    object : AccompanistWebViewClient() {
      override fun onPageStarted(
        view: WebView,
        url: String?,
        favicon: Bitmap?
      ) {
        super.onPageStarted(view, url, favicon)
      }
    }
  }
  ColumnPage {
    if (showTitle) {
      AppTopBarView(title = title, clickReturn = {
        navController.popBackStack()
      })
    }
    WebView(modifier = Modifier.fillMaxWidth().weight(1f).alpha(0.99f), state = state, onCreated = { webView ->
      webView.settings.javaScriptEnabled = true
    }, client = webClient)
  }
  if (state.isLoading) {
    LoadingProgress()
  }
}