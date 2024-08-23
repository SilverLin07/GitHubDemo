package com.example.githubdemo.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.ui.theme.BgBtnDisabled
import com.example.githubdemo.ui.theme.FontWhite
import com.example.githubdemo.ui.theme.PrimaryColor


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 12:48
 */
@Composable
fun TextButton(
  content: String,
  modifier: Modifier,
  paddingValues: PaddingValues = PaddingValues(0.dp),
  radius: Dp,
  backgroundColor: Color = PrimaryColor,
  backgroundDisabledColor: Color = BgBtnDisabled,
  borderColor: Color = Color.Transparent,
  fontColor: Color = FontWhite,
  fontSize: TextUnit = 18.sp,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(radius))
      .background(if (enabled) backgroundColor else backgroundDisabledColor)
      .border(1.dp, borderColor, RoundedCornerShape(radius))
      .padding(paddingValues)
      .noRippleClickable(milliSec = 1000) {
        if (enabled) {
          onClick()
        }
      },
  ) {
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = content,
      color = fontColor,
      fontSize = fontSize,
      textAlign = TextAlign.Center
    )
  }
}