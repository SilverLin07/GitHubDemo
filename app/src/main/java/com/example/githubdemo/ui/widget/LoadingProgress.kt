package com.example.githubdemo.ui.widget

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.R

/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:18
 */
// 默认的菊花加载
@Composable
fun LoadingProgress(OnOutsideClick:()->Unit={}) {

  var showMunDialog = remember {
    mutableStateOf(false)
  }
  //返回键
  BackHandler(enabled = showMunDialog.value) {
    OnOutsideClick()
  }

  Surface(color = Color.Transparent) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .noRippleClickable { OnOutsideClick() }
        .background(Color.Transparent),
      contentAlignment = Alignment.Center
    ) {
      Box(
        Modifier
          .size(60.dp)
          .background(
            Color(0x90000000),
            RoundedCornerShape(8.dp)
          )) {
        Image(
          painter = painterResource(id = R.drawable.load),
          contentDescription = null,
          modifier = Modifier
            .align(Alignment.Center)
            .size(35.dp)
            .rotate(
              ssRepeatedFloatAnimation(
                0f,
                360f,
                2000
              )
            )
        )
      }
    }
  }
}

@Composable
fun ssRepeatedFloatAnimation(
  initialValue: Float,
  targetValue: Float,
  durationMillis: Int
): Float {
  val repeatedFloat by rememberInfiniteTransition().animateValue(
    initialValue = initialValue,
    targetValue = targetValue,
    typeConverter = Float.VectorConverter,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )
  return repeatedFloat
}