package com.example.githubdemo.ui.page

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.blankj.utilcode.util.LogUtils
import com.example.common.data.constants.Router
import com.example.githubdemo.ui.widget.AppBottomNavBarView

@Composable
fun AppPage(navController: NavHostController) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route
  val showBottomBar = rememberSaveable {
    mutableStateOf(true)
  }

  LaunchedEffect(key1 = currentRoute, block = {
    LogUtils.d("currentRoute $currentRoute")
    showBottomBar.value = currentRoute != null && isMainPage(currentRoute)
  })

  Scaffold(bottomBar = {
    if (showBottomBar.value) {
      AppBottomNavBarView(navController = navController)
    }
  },
    content = { mPaddingValues ->
    NavHost(navController = navController,
      startDestination = Router.EXPLORE_PAGE) {

      composable(route = Router.SPLASH_PAGE) {
        SplashPage(navController)
      }

      composable(route = Router.EXPLORE_PAGE) {
        ExplorePage(hiltViewModel(), navController)
      }

      composable(route = Router.SEARCH_PAGE) {
        SearchPage(hiltViewModel(), navController, mPaddingValues)
      }

      composable(route = Router.MINE_PAGE) {
        MinePage(hiltViewModel(), navController, mPaddingValues)
      }

      composable(route = Router.STARRED_PAGE) {
        StarredPage(hiltViewModel(), navController,)
      }

      composable(route = Router.MINE_SETTING_PAGE) {
        MineSettingPage(hiltViewModel(), navController)
      }

      composable(route = Router.WEB_PAGE) {
        val url = it.arguments?.getString("url")
        WebPage(url = url ?: "", navController = navController)
      }

      composable(route = Router.IMAGE_PAGE) {
        val url = it.arguments?.getString("url")
        url?.let { imageUrl -> ImagePage(imageUrl, navController) }
      }

    }}
  )
}

fun isMainPage(navigatorName: String): Boolean {
  return (navigatorName == Router.EXPLORE_PAGE || navigatorName == Router.SEARCH_PAGE
          || navigatorName == Router.MINE_PAGE)
}