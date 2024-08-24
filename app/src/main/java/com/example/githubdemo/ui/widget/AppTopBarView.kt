package com.example.githubdemo.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.data.constants.SpecConstants
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.R
import com.example.githubdemo.ui.theme.BgContent
import com.example.githubdemo.ui.theme.FontPrimary


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:13
 */
@Composable
fun AppTopBarView(title: String = "", showReturnBtn: Boolean = true, clickReturn: () -> Unit = {}) {
  Box(
    modifier = Modifier
      .background(Color.White)
      .padding(horizontal = 12.dp)
      .height(SpecConstants.AppBarHeight)
      .fillMaxWidth()
  ) {
    if (showReturnBtn) {
      Icon(
        modifier = Modifier
          .align(Alignment.CenterStart)
          .noRippleClickable {
            clickReturn()
          }, painter = painterResource(id = R.drawable.ic_return_btn), tint = Color.Black, contentDescription = null
      )
    }
    if (title.isNotEmpty()) {
      Text(
        modifier = Modifier
          .align(Alignment.Center)
          .widthIn(max = 312.dp),
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = FontPrimary
      )
    }
  }
}

@Composable
fun AppTopBarViewWithOpt(
  title: String = "",
  showReturnBtn: Boolean = true,
  clickReturn: () -> Unit = {},
  optId: Int,
  subOptId: Int = 0,
  subTxt: String = "",
  clickOpt: () -> Unit = {},
  clickSubOpt: () -> Unit = {},
  showOpt: Boolean = true
) {
  Box(
    modifier = Modifier
      .height(SpecConstants.AppBarHeight)
      .fillMaxWidth()
      .background(BgContent)
      .padding(horizontal = 12.dp)
  ) {
    if (showReturnBtn) {
      Icon(
        modifier = Modifier
          .align(Alignment.CenterStart)
          .noRippleClickable {
            clickReturn()
          },
        painter = painterResource(id = R.drawable.ic_return_btn),
        tint = Color.Black,
        contentDescription = null
      )
    }
    if (title.isNotEmpty()) {
      Text(
        modifier = Modifier
          .align(Alignment.Center)
          .widthIn(max = 312.dp),
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = FontPrimary
      )
    }
    if (subOptId != 0) {
      Icon(
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .padding(end = 42.dp)
          .noRippleClickable {
            clickSubOpt()
          },
        painter = painterResource(id = subOptId),
        tint = Color.Black,
        contentDescription = null
      )
    }
    if (showOpt) {
      if (subTxt.isNotEmpty()) {
        TextButton(content = subTxt, modifier = Modifier
          .width(60.dp)
          .height(30.dp)
          .align(Alignment.CenterEnd), radius = 4.dp, fontSize = 16.sp) {
          clickOpt()
        }
      } else {
        Icon(
          modifier = Modifier
            .align(Alignment.CenterEnd)
            .noRippleClickable {
              clickOpt()
            },
          painter = painterResource(id = optId),
          tint = Color.Black,
          contentDescription = null
        )
      }
    }
  }
}