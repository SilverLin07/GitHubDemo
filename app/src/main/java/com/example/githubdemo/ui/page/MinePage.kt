package com.example.githubdemo.ui.page

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.ColumnPage
import com.example.githubdemo.ui.widget.TextButton
import com.example.githubdemo.viewmodel.MineViewModel


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 12:47
 */
@Composable
fun MinePage(viewModel: MineViewModel, navController: NavController) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  ColumnPage {
    AppTopBarView("Mine", showReturnBtn = false)
    TextButton(content = "Login", modifier = Modifier
      .width(100.dp)
      .height(40.dp)
      .align(Alignment.CenterHorizontally), radius = 5.dp) {
        viewModel.launchAuthorizationUrl(navController.context)
    }
  }
}