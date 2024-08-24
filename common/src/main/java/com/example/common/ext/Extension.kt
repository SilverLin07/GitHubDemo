package com.example.common.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.example.common.base.AppSessionUtil


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 12:49
 */
fun Context.findActivity(): ComponentActivity? = when (this) {
  is ComponentActivity -> this
  is ContextWrapper -> baseContext.findActivity()
  else -> null
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@OptIn(ExperimentalFoundationApi::class)
inline fun Modifier.noRippleClickable(milliSec: Int = 500, noinline onLongClick: ()->Unit = {}, crossinline onClick: ()->Unit):
        Modifier = composed {
  combinedClickable(indication = null,
    interactionSource = remember { MutableInteractionSource() }, onLongClick = onLongClick) {
    // 指定时间内只允许点击一次
    if (AppSessionUtil.lastClickTime == -1L || System.currentTimeMillis() - AppSessionUtil.lastClickTime > milliSec) {
      onClick()
      AppSessionUtil.lastClickTime = System.currentTimeMillis()
    }
  }
}