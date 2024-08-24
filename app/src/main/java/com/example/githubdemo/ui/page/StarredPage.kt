package com.example.githubdemo.ui.page

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.data.constants.Router
import com.example.common.ext.OnBottomReached
import com.example.githubdemo.R
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.AppTopBarViewWithOpt
import com.example.githubdemo.ui.widget.ColumnPage
import com.example.githubdemo.ui.widget.LoadingProgress
import com.example.githubdemo.ui.widget.SearchRepositoryItemView
import com.example.githubdemo.viewmodel.MineViewModel
import com.example.githubdemo.viewmodel.StarredViewModel


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-24 00:52
 */
@Composable
fun StarredPage(viewModel: StarredViewModel, navController: NavController) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val listState = rememberLazyListState()

  if (!state.repositoryList.isNullOrEmpty()) {
    listState.OnBottomReached {
      viewModel.getStarredRepositories()
    }
  }

  ColumnPage {
    AppTopBarView(stringResource(id = R.string.starred), showReturnBtn = true) {
      navController.popBackStack()
    }

    state.repositoryList?.let { list ->
      LazyColumn(state = listState) {
        items(list) {
          SearchRepositoryItemView(item = it) {
            navController.navigate("${Router.WEB_BASE_PAGE}/${Uri.encode(it.htmlUrl)}")
          }
        }
      }
    }
  }
  if (state.loading) {
    LoadingProgress()
  }
}