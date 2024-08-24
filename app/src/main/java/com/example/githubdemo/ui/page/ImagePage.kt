package com.example.githubdemo.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.ColumnPage


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 21:16
 */
@Composable
fun ImagePage(url: String, navController: NavController) {
  ColumnPage {
    AppTopBarView(clickReturn = {
      navController.popBackStack()
    })
    AsyncImage(model = url, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillWidth, contentDescription = null)
  }
}