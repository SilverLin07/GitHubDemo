package com.example.githubdemo.ui.page

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.ColumnPage


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 16:55
 */
@Composable
fun ExplorePage() {
  ColumnPage {
    AppTopBarView("Explore", showReturnBtn = false)
    Text(text = "Explore Page")
  }
}