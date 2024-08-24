package com.example.githubdemo.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubdemo.ui.theme.BgContent
import com.example.githubdemo.ui.theme.BgPage
import com.example.githubdemo.ui.theme.FontPrimary


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 23:16
 */
@Composable
fun RowSwitch(text: String, checked: Boolean = false,
              horizontalPadding: Dp = 0.dp,
              verticalPadding: Dp = 0.dp,
              check: (boolean: Boolean) -> Unit = {}) {
  Surface(color = BgContent) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = horizontalPadding,
          vertical = verticalPadding
        ),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = text, color = FontPrimary, fontSize = 16.sp)
      Spacer(modifier = Modifier.weight(1f))
      Switch(checked = checked, onCheckedChange = check, colors = SwitchDefaults.colors(
        checkedTrackColor = Color.Black,
        checkedTrackAlpha = 1f,
        uncheckedTrackColor = BgPage,
        uncheckedTrackAlpha = 1f,
        checkedThumbColor = White
      ))
    }
  }
}