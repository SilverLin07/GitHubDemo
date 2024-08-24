package com.example.githubdemo.ui.page

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.blankj.utilcode.util.ToastUtils
import com.example.common.data.constants.Router
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.R
import com.example.githubdemo.ui.theme.FontHint
import com.example.githubdemo.ui.theme.FontPrimary
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.ColumnPage
import com.example.githubdemo.ui.widget.LoadingProgress
import com.example.githubdemo.ui.widget.RowIconText
import com.example.githubdemo.ui.widget.RowSwitch
import com.example.githubdemo.ui.widget.TextButton
import com.example.githubdemo.viewmodel.MineSettingViewModel
import java.util.Locale


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 20:47
 */
@Composable
fun MineSettingPage(viewModel: MineSettingViewModel, navController: NavController) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  ColumnPage {
    AppTopBarView(stringResource(id = R.string.settings), clickReturn = {navController.popBackStack()})

    if (state.loading) {
      LoadingProgress()
    } else if (state.userInfoBean != null) {
      Row(modifier = Modifier.padding(horizontal = 20.dp)) {
        AsyncImage(model = state.userInfoBean!!.avatarUrl, contentDescription = null,
          modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)
            .noRippleClickable {
              navController.navigate(
                "${
                  Router.IMAGE_BASE_PAGE
                }/${Uri.encode(state.userInfoBean!!.avatarUrl)}"
              )
            })

        Spacer(modifier = Modifier.width(20.dp))
        Column {
          Text(text = "${state.userInfoBean!!.name}(${state.userInfoBean!!.login})", fontSize = 18.sp, color = FontPrimary)
          Spacer(modifier = Modifier.height(4.dp))
          Row {
            Text(text = "followers:${state.userInfoBean!!.followers}", fontSize = 14.sp, color = FontHint)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "following:${state.userInfoBean!!.following}", fontSize = 14.sp, color = FontHint)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Row {
            Text(text = "company:${state.userInfoBean!!.company}", fontSize = 14.sp, color = FontHint)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "email:${state.userInfoBean!!.email}", fontSize = 14.sp, color = FontHint)
          }
        }
      }
      Spacer(modifier = Modifier.height(30.dp))
      RowIconText(imgRes = R.drawable.ic_star_small, imgSize = 30.dp, text = stringResource(id = R.string.starred), horizontalPadding = 10.dp) {
        navController.navigate(Router.STARRED_PAGE)
      }
      Divider()
      RowIconText(text = stringResource(id = R.string.switch_language), horizontalPadding = 10.dp, showIcon = true) {
        viewModel.updateLocale(navController.context)
      }
      Spacer(modifier = Modifier.weight(1f))
      TextButton(content = stringResource(id = R.string.logout), modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .width(355.dp)
        .height(45.dp), radius = 6.dp) {
        viewModel.logOut()
        navController.navigate(Router.EXPLORE_PAGE)
        ToastUtils.showShort("Logout Success!")
      }
      Spacer(modifier = Modifier.height(60.dp))
    }
  }
}