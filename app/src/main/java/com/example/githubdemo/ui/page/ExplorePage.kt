package com.example.githubdemo.ui.page

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.common.data.constants.UrlConstants.EXPLORE_URL
import com.example.githubdemo.viewmodel.ExploreViewModel


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 16:55
 */
@Composable
fun ExplorePage(viewModel: ExploreViewModel, navController: NavController) {
  WebPage(url = EXPLORE_URL, showTitle = false, navController = navController)
}