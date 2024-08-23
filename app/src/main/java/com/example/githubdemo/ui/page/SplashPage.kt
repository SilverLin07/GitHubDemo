package com.example.githubdemo.ui.page

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.common.data.constants.UrlConstants.GITHUB_URL


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 12:47
 */
@Composable
fun SplashPage(navController: NavController) {
  WebPage(url = GITHUB_URL, navController = navController)
}