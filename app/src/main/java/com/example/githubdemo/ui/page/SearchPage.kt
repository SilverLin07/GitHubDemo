package com.example.githubdemo.ui.page

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.common.data.constants.Constants.SEARCH_FILTER_LANGUAGE
import com.example.common.data.constants.Constants.SEARCH_FILTER_SORT
import com.example.common.data.constants.Router
import com.example.common.ext.OnBottomReached
import com.example.common.ext.noRippleClickable
import com.example.common.ext.scrollToTop
import com.example.githubdemo.R
import com.example.githubdemo.data.bean.RepositoryItem
import com.example.githubdemo.ui.theme.BgContent
import com.example.githubdemo.ui.theme.BgDialog
import com.example.githubdemo.ui.theme.BgGray
import com.example.githubdemo.ui.theme.FontEmphasis
import com.example.githubdemo.ui.theme.FontPrimary
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.ColumnPage
import com.example.githubdemo.ui.widget.LoadingProgress
import com.example.githubdemo.ui.widget.SearchInputArea
import com.example.githubdemo.ui.widget.SearchRepositoryItemView
import com.example.githubdemo.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 16:55
 */
@Composable
fun SearchPage(viewModel: SearchViewModel, navController: NavController, paddingValues: PaddingValues) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val input = rememberSaveable {
    mutableStateOf("")
  }
  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  val filterTabIndex = rememberSaveable {
    mutableIntStateOf(-1)
  }

  if (!state.repositoryList.isNullOrEmpty()) {
    listState.OnBottomReached {
      viewModel.search(input.value)
    }
  }

  ColumnPage(modifier = Modifier.padding(paddingValues)) {
    Spacer(modifier = Modifier.height(20.dp))
    SearchInputArea(modifier = Modifier
      .align(Alignment.CenterHorizontally)
      .padding(horizontal = 10.dp)
      .fillMaxWidth()
      .height(34.dp), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), bgColor = BgGray,
      radius = 18.dp, text = input,
      hintWord = stringResource(id = R.string.search), onStopInput = {
        viewModel.search(input.value, reset = true)
      }, onCancelClick = {
      input.value = ""
    }, keyboardActions = KeyboardActions(onSearch = {
        viewModel.search(input.value, reset = true)
      })
    )

    Spacer(modifier = Modifier.height(20.dp))
    SearchRowFilter(state.language, state.sortBy, tabIndex = filterTabIndex, onClick = {
      viewModel.showRowFilterType(it)
    })

    Box {
      state.repositoryList?.let { list ->
        LazyColumn(state = listState, modifier = Modifier.testTag("searchLazyColumn")) {
          items(list) {
            SearchRepositoryItemView(item = it, onClick = {
              navController.navigate("${Router.WEB_BASE_PAGE}/${Uri.encode(it.htmlUrl)}")
            }, showIssues = state.showIssues, onClickIssues = {
              navController.navigate("${Router.EDIT_ISSUES_BASE_PAGE}/${it.owner.login}/${it.name}")
            })
          }
        }
      }

      if (state.showRowFilterType != -1) {
        Surface(modifier = Modifier
          .fillMaxSize()
          .noRippleClickable {
            viewModel.showRowFilterType(-1)
            filterTabIndex.intValue = -1
          }, color = BgDialog
        ) {}
      }

      androidx.compose.animation.AnimatedVisibility(
        visible = state.showRowFilterType == SEARCH_FILTER_LANGUAGE,
        enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(
          initialAlpha = 0.3f
        ),
        exit = fadeOut()
      ) {
        RowFilterLanguages(state.language, clickItem = { s ->
          viewModel.setTabLanguage(s)
          viewModel.search(input.value, s, state.sortBy, reset = true) {
            scope.launch {
              delay(30)
              listState.scrollToTop()
            }
          }
          viewModel.showRowFilterType(-1)
          filterTabIndex.intValue = -1
        })
      }

      androidx.compose.animation.AnimatedVisibility(
        visible = state.showRowFilterType == SEARCH_FILTER_SORT,
        enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(
          initialAlpha = 0.3f
        ),
        exit = fadeOut()
      ) {
        RowFilterSort(state.sortBy, clickItem = { s ->
          viewModel.setTabSort(s)
          viewModel.search(input.value, state.language, s, reset = true) {
            scope.launch {
              delay(30)
              listState.scrollToTop()
            }
          }
          viewModel.showRowFilterType(-1)
          filterTabIndex.intValue = -1
        })
      }
    }
  }

  if (state.loading) {
    LoadingProgress()
  }
}

@Composable
fun SearchRowFilter(tabLanguage: String, tabSort: String, tabIndex: MutableState<Int>, onClick: (Int) -> Unit = {}) {
  val tabTitles = listOf(tabLanguage, tabSort)
  Row(
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .height(44.dp)
  ) {
    tabTitles.forEachIndexed { index, title ->
      val isSelected = tabIndex.value == index
      // tab
      Tab(modifier = Modifier.widthIn(max = 150.dp),
        selected = isSelected, onClick = {
          if (tabIndex.value != index) {
            tabIndex.value = index
          } else {
            tabIndex.value = -1
          }
          onClick(tabIndex.value)
        }) {
        val fontSize = 14.sp
        val fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            modifier = Modifier.padding(
              vertical = 6.dp
            ),
            text = title,
            fontSize = fontSize,
            color = if (isSelected) FontEmphasis else FontPrimary,
            fontWeight = fontWeight
          )
          Spacer(modifier = Modifier.width(4.dp))
          if (isSelected) {
            Icon(painter = painterResource(id = R.drawable.ic_arrow_up_tri), tint = FontEmphasis, contentDescription = null, modifier = Modifier.size(12.dp))
          } else {
            Icon(painter = painterResource(id = R.drawable.ic_arrow_down_tri), tint = FontPrimary, contentDescription = null, modifier = Modifier.size(12.dp))
          }
        }
      }
    }
  }
}

@Composable
fun RowFilterLanguages(currentSort: String, clickItem: (String) -> Unit) {
  val sortList = remember {
    listOf("Java", "Kotlin", "C", "C++", "JavaScript", "HTML", "Dart", "C#", "Python")
  }
  Column(modifier = Modifier
    .fillMaxWidth()
    .background(BgContent)
    .padding(start = 12.dp, end = 12.dp, bottom = 18.dp)) {
    sortList.forEachIndexed { index, s ->
      Text(text = s, fontSize = 12.sp, color = if (currentSort == sortList[index]) FontEmphasis else FontPrimary, modifier = Modifier
        .padding(vertical = 12.dp)
        .fillMaxWidth()
        .noRippleClickable {
          clickItem(s)
        })
    }
  }
}

@Composable
fun RowFilterSort(currentSort: String, clickItem: (String) -> Unit) {
  val sortList = remember {
    listOf("Best match", "Most stars", "Most forks", "Recently updated")
  }
  Column(modifier = Modifier
    .fillMaxWidth()
    .background(BgContent)
    .padding(start = 12.dp, end = 12.dp, bottom = 18.dp)) {
    sortList.forEachIndexed { index, s ->
      Text(text = s, fontSize = 12.sp, color = if (currentSort == sortList[index]) FontEmphasis else FontPrimary, modifier = Modifier
        .fillMaxWidth()
        .noRippleClickable {
          clickItem(s)
        }
        .padding(vertical = 12.dp))
    }
  }
}
