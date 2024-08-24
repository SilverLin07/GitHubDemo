package com.example.common.ext

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 22:22
 */
@Composable
fun LazyListState.OnBottomReached(buffer: Int = 0, fixedNum: Int = 0, onLoadMore: () -> Unit) {
  val shouldLoadMore = remember {
    derivedStateOf {
      val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true

//      LogUtils.d("OnBottomReached " +
//        "lastVisibleItem=${lastVisibleItem.index}    lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer="+( lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer)
//      )
      lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer && lastVisibleItem.index >= fixedNum - 1 -buffer
    }
  }

  LaunchedEffect(shouldLoadMore) {
    snapshotFlow {shouldLoadMore.value}.collect {
      if (it) onLoadMore()
    }
  }
}

suspend fun LazyListState.scrollToTop() {
  scrollToItem(0)
}