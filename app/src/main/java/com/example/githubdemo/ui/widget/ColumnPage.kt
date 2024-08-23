package com.example.githubdemo.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.ui.theme.BgContent
import com.google.accompanist.systemuicontroller.rememberSystemUiController


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:12
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColumnPage(
  showTopPadding: Boolean = true,
  showBottomPadding: Boolean = true,
  bgColor: Color = BgContent,
  statusColor: Color = BgContent,
  darkIcons: Boolean = true,
  modifier: Modifier = Modifier,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  needKeyboardPadding: Boolean = true,
  clickPage: () -> Unit = {},
  pageContent: @Composable ColumnScope.() -> Unit,
) {
  val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
  val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()
  val imePadding = WindowInsets.ime.asPaddingValues().calculateBottomPadding() - systemBarsPadding.calculateBottomPadding()
  val systemUiController = rememberSystemUiController()
  val keyboardState by KeyboardAsState()
  val keyboard = LocalSoftwareKeyboardController.current

  DisposableEffect(systemUiController, darkIcons) {
    systemUiController.setSystemBarsColor(
      color = statusColor,
      darkIcons = darkIcons
    )

    // setStatusBarColor() and setNavigationBarColor() also exist
    onDispose {}
  }

  val columnModifier = modifier
    .background(bgColor)
    .padding(
      top = if (showTopPadding) systemBarsPadding.calculateTopPadding() else 0.dp,
      bottom = if (showBottomPadding) navigationBarsPadding.calculateBottomPadding() else 0.dp
    )

//  LogUtils.d("imePadding ${imePadding.value}")
  Column(
    modifier = columnModifier.noRippleClickable {
      clickPage()
      if (keyboardState) {
        keyboard?.hide()
      }
    }, verticalArrangement = verticalArrangement,
    horizontalAlignment = horizontalAlignment
  ) {
    pageContent()
    // 软键盘
    if(needKeyboardPadding && keyboardState && imePadding > 0.dp) {
      Box(modifier = Modifier.padding(bottom = imePadding))
    }
  }
}