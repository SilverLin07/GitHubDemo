package com.example.githubdemo.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.R
import com.example.githubdemo.ui.theme.BgContent
import com.example.githubdemo.ui.theme.FontHint
import com.example.githubdemo.ui.theme.FontPrimary


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 20:51
 */
@Composable
fun RowIconText(
  imgRes: Int? = null,
  imgUrl: String? = null,
  imgSize: Dp = 16.dp,
  text: String,
  textSize: TextUnit = 16.sp,
  subText: String? = null,
  subTextSize: TextUnit = 14.sp,
  bgColor: Color = BgContent,
  showIcon: Boolean = true,
  iconColor: Color = FontPrimary,
  horizontalPadding: Dp = 0.dp,
  verticalPadding: Dp = 15.dp,
  singleLine: Boolean = false,
  fontColor: Color = FontPrimary,
  subTextFontColor: Color = FontHint,
  fontWeight: FontWeight = FontWeight.Normal,
  subFontWeight: FontWeight = FontWeight.Normal,
  click: () -> Unit = {}
) {
  Surface(color = BgContent) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(bgColor)
        .padding(
          horizontal = horizontalPadding,
          vertical = verticalPadding
        )
        .noRippleClickable {
          click()
        },
      verticalAlignment = Alignment.CenterVertically
    ) {
      imgRes?.let {
        Image(
          painter = painterResource(id = imgRes),
          modifier = Modifier.size(imgSize),
          contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      imgUrl?.let {
        AsyncImage(model = it, modifier = Modifier.size(imgSize), contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
      }
      Text(
        text = text,
        color = fontColor,
        fontWeight = fontWeight,
        fontSize = textSize,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis
      )
      Spacer(modifier = Modifier.weight(1f))
      subText?.let {
        Text(text = subText, color = subTextFontColor, fontWeight = subFontWeight, fontSize = subTextSize)
      }
      if (showIcon) {
        Icon(
          painter = painterResource(id = R.drawable.ic_arrow_more),
          tint = iconColor,
          modifier = Modifier.size(
            14.dp
          ),
          contentDescription = null
        )
      }
    }
  }
}