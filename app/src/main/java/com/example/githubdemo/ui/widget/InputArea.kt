package com.example.githubdemo.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.util.FunctionUtil
import com.example.githubdemo.ui.theme.BgContent
import com.example.githubdemo.ui.theme.FontHint


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-24 13:11
 */
@Composable
fun InputArea(
  modifier: Modifier,
  text: MutableState<String>,
  fontSize: TextUnit = 16.sp,
  onValueChange: (String) -> Unit = {},
  onFocusChange: (focus: Boolean) -> Unit = {},
  onStopInput: (String) -> Unit = {},
  paddingValues: PaddingValues = PaddingValues(10.dp),
  keyboardOptions: KeyboardOptions = KeyboardOptions(),
  keyboardActions: KeyboardActions = KeyboardActions(),
  hintWord: String,
  hintWordSize: TextUnit = 16.sp,
  bgColor: Color = BgContent,
  borderColor: Color? = null,
  shape: Shape = RoundedCornerShape(4.dp),
  focusRequester: FocusRequester? = null,
  singleLine: Boolean = false,
  maxNum: Int = -1,
  showMaxNumHint: Boolean = true
) {
  BasicTextField(value = if (maxNum > 0 && text.value.codePointCount(0, text.value.length) >= maxNum) text.value.substring(
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
  }, textStyle = TextStyle(fontSize = fontSize),
    singleLine = singleLine,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    decorationBox = { innerTextField ->
      Surface(
        color = bgColor,
        shape = shape,
        border = if (borderColor != null) BorderStroke(0.5.dp, color = borderColor) else null
      ) {
        Box(
          modifier = modifier.padding(paddingValues),
        ) {
          if (text.value.isEmpty()) {
            Text(
              text = hintWord,
              fontSize = hintWordSize,
              color = FontHint,
            )
          }
          Box(modifier = Modifier.padding(bottom = if (maxNum > 0) 14.dp else 0.dp)) {
            innerTextField()
          }
          if (maxNum > 0 && showMaxNumHint) {
            Text(
              text = "${text.value.codePointCount(0, text.value.length)}/$maxNum",
              modifier = Modifier.align(Alignment.BottomEnd),
              fontSize = 12.sp,
              color = FontHint
            )
          }
        }
      }
    })
}