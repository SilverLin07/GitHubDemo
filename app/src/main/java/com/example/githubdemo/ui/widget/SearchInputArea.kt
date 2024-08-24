package com.example.githubdemo.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.ext.noRippleClickable
import com.example.common.util.FunctionUtil
import com.example.githubdemo.R
import com.example.githubdemo.ui.theme.BgContent
import com.example.githubdemo.ui.theme.FontHint


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 18:18
 */
@Composable
fun SearchInputArea(
  modifier: Modifier,
  text: MutableState<String>,
  onValueChange: (String) -> Unit = {},
  onFocusChange: (focus: Boolean) -> Unit = {},
  onStopInput: (String) -> Unit = {},
  singleLine: Boolean = false,
  keyboardOptions: KeyboardOptions = KeyboardOptions(),
  keyboardActions: KeyboardActions = KeyboardActions(),
  hintWord: String,
  bgColor: Color = BgContent,
  borderColor: Color? = null,
  radius: Dp = 4.dp,
  focusRequester: FocusRequester? = null,
  maxNum: Int = -1,
  onCancelClick: () -> Unit = {}
) {
  BasicTextField(singleLine = singleLine, value = if (maxNum > 0 && text.value.codePointCount(0, text.value.length) >= maxNum) text.value.substring(
    0,
    text.value.offsetByCodePoints(0, maxNum)
  ) else text.value, modifier = modifier.onFocusChanged {
    onFocusChange(it.isFocused)
  }, onValueChange = {
    val realTxt = if (maxNum > 0 && it.codePointCount(0, it.length) >= maxNum) it.substring(0, it.offsetByCodePoints(0, maxNum)) else it
    text.value = realTxt
    onValueChange(realTxt)
    // 防抖
    FunctionUtil.debounce {
      onStopInput(realTxt)
    }
  },
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    decorationBox = { innerTextField ->
      Surface(
        color = bgColor,
        shape = RoundedCornerShape(radius),
        border = if (borderColor != null) BorderStroke(0.5.dp, color = borderColor) else null
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 10.dp)
        ) {
          Icon(
            painter = painterResource(id = R.drawable.ic_search_gray),
            modifier = Modifier.size(16.dp),
            tint = FontHint,
            contentDescription = null
          )
          Spacer(modifier = Modifier.width(5.dp))
          Box(
            modifier = Modifier.weight(1f),
          ) {
            Text(
              modifier = Modifier.fillMaxWidth(),
              text = if (text.value.isEmpty()) hintWord else "",
              fontSize = 14.sp,
              color = FontHint,
            )

            innerTextField()
            if (maxNum > 0) {
              Text(
                text = "${text.value.codePointCount(0, text.value.length)}/$maxNum",
                modifier = Modifier.align(Alignment.BottomEnd),
                fontSize = 12.sp,
                color = FontHint
              )
            }
          }
          Icon(
            painter = painterResource(id = R.drawable.ic_cancel_gray),
            modifier = Modifier
              .size(17.dp)
              .noRippleClickable {
                onCancelClick()
              },
            tint = FontHint,
            contentDescription = null
          )
        }
      }
    })
}