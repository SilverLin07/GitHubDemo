package com.example.githubdemo.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.common.data.constants.Router
import com.example.githubdemo.R
import com.example.githubdemo.ui.theme.BgContent
import com.example.githubdemo.ui.theme.PrimaryColor
import com.example.githubdemo.ui.theme.PrimaryVariant


/**
 * 底部导航View
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2022-04-20 10:20
 */
@Composable
fun AppBottomNavBarView(navController: NavHostController, unreadMsgCount: Int = 0) {
  val bottomNavList = listOf(
    BottomNavRoute.Explore,
    BottomNavRoute.Search,
    BottomNavRoute.Mine
  )
  val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
  BottomNavigation(modifier = Modifier.padding(bottom = systemBarsPadding.calculateBottomPadding())) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    bottomNavList.forEach { item ->
      val isSelected = currentDestination?.hierarchy?.any {
        it.route == item.routeName
      } == true
      BottomNavigationItem(
        modifier = Modifier.background(color = BgContent),
        selectedContentColor = PrimaryColor,
        unselectedContentColor = PrimaryVariant,
        icon = {
          if (isSelected) {
            Image(painter = painterResource(id = item.selectIconId), contentDescription = null)
          } else {
            Image(painter = painterResource(id = item.defalutIconId), contentDescription = null)
          }
        },
        label = {
          Text(fontSize = 10.sp, text = item.string)
        },
        selected = isSelected,
        onClick = {
          if (currentDestination?.route != item.routeName) {
            navController.navigate(item.routeName) {
              popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
              }
              restoreState = true
              launchSingleTop = true
            }
          }
        })
    }
  }
}

sealed class BottomNavRoute(var routeName: String, var string: String, @DrawableRes var defalutIconId: Int, @DrawableRes var selectIconId: Int) {
  object Explore: BottomNavRoute(Router.EXPLORE_PAGE, "explore", R.drawable.ic_tab_community_default, R.drawable.ic_tab_community_select)
  object Search: BottomNavRoute(Router.SEARCH_PAGE, "search", R.drawable.ic_tab_find_default, R.drawable.ic_tab_find_select)
  object Mine: BottomNavRoute(Router.MINE_PAGE, "mime", R.drawable.ic_tab_mine_default, R.drawable.ic_tab_mine_select)
}