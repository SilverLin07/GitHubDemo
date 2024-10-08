package com.example.githubdemo.ui.page

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.data.constants.Router
import com.example.common.ext.OnBottomReached
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.R
import com.example.githubdemo.ui.theme.BgSignIn
import com.example.githubdemo.ui.theme.FontPrimary
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.AppTopBarViewWithOpt
import com.example.githubdemo.ui.widget.ColumnPage
import com.example.githubdemo.ui.widget.LoadingProgress
import com.example.githubdemo.ui.widget.SearchRepositoryItemView
import com.example.githubdemo.ui.widget.TextButton
import com.example.githubdemo.viewmodel.MineViewModel


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 12:47
 */
@Composable
fun MinePage(viewModel: MineViewModel, navController: NavController, paddingValues: PaddingValues) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val listState = rememberLazyListState()

  if (!state.repositoryList.isNullOrEmpty()) {
    listState.OnBottomReached {
      viewModel.getMyRepositories()
    }
  }

  ColumnPage(modifier = Modifier.padding(paddingValues)) {
    AppTopBarViewWithOpt(stringResource(id = R.string.mine), showReturnBtn = false,
      optId = R.drawable.mine_ic_setting,
      clickOpt = {
      navController.navigate(route = Router.MINE_SETTING_PAGE)
    }, showOpt = !state.showSignIn)

    Box {
      Column(modifier = Modifier.fillMaxWidth()) {
        if (state.showSignIn) {
          SignInView {
            viewModel.launchAuthorizationUrl(navController.context)
          }
        }

        state.repositoryList?.let { list ->
          LazyColumn(state = listState, modifier = Modifier.testTag("mineLazyColumn")) {
            items(list) {
              SearchRepositoryItemView(item = it, onClick = {
                navController.navigate("${Router.WEB_BASE_PAGE}/${Uri.encode(it.htmlUrl)}")
              }, showIssues = true, onClickIssues = {
                navController.navigate("${Router.EDIT_ISSUES_BASE_PAGE}/${it.owner.login}/${it.name}")
              })
            }
          }
        }
      }

      if (state.loading) {
        LoadingProgress()
      }
    }
  }
}

@Composable
fun ColumnScope.SignInView(clickSignIn: () -> Unit) {
  Spacer(modifier = Modifier.height(200.dp))
  Image(painter = painterResource(id = R.drawable.ic_github), contentDescription = null, modifier = Modifier
    .size(80.dp)
    .align(Alignment.CenterHorizontally))
  Spacer(modifier = Modifier.height(20.dp))
  Text(text = "Sign in to GitHub", fontSize = 30.sp, color = FontPrimary, modifier = Modifier.align(Alignment.CenterHorizontally), fontWeight = FontWeight.Bold)
  Spacer(modifier = Modifier.height(20.dp))
  TextButton(content = "Sign in with accessToken", modifier = Modifier
    .width(365.dp)
    .height(46.dp)
    .align(Alignment.CenterHorizontally), backgroundColor = BgSignIn, radius = 8.dp) {
    clickSignIn()
  }
  Spacer(modifier = Modifier.weight(1f))
}