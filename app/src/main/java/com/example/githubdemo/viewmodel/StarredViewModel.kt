package com.example.githubdemo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.base.Reducer
import com.example.common.data.constants.Constants
import com.example.common.util.DataStoreUtils
import com.example.common.util.eventbus.AuthEvent
import com.example.common.util.eventbus.EventBus
import com.example.githubdemo.data.network.MainService
import com.example.githubdemo.model.StarredEvent
import com.example.githubdemo.model.StarredState
import com.example.githubdemo.model.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:52
 */
@HiltViewModel
class StarredViewModel @Inject constructor(val service: MainService, val eventBus: EventBus): BaseViewModel<StarredState, StarredEvent>() {
  override val state: StateFlow<StarredState>
    get() = reducer.state
  private val reducer = HomeReducer(StarredState())
  private fun sendEvent(event: StarredEvent) {
    reducer.sendEvent(event)
  }

  class HomeReducer(initVal: StarredState): Reducer<StarredState, StarredEvent>(initVal) {
    override fun reduce(oldState: StarredState, event: StarredEvent) {
      when (event) {
        is StarredEvent.SetLoading -> {
          setState(oldState.copy(loading = event.loading))
        }
        is StarredEvent.SetRepositoryList -> {
          event.repositoryList.forEach { it.formatTime() }
          if (event.page == 1) {
            setState(oldState.copy(repositoryList = event.repositoryList))
          } else {
            val newList = oldState.repositoryList?.toMutableList()
            newList?.addAll(event.repositoryList)
            setState(oldState.copy(repositoryList = newList))
          }
        }
      }
    }
  }

  init {
    if (DataStoreUtils.readStringData(DataStoreUtils.ACCESS_TOKEN, "").isNotBlank()) {
      getStarredRepositories(true)
    }
  }

  var page = 0

  // action
  fun getStarredRepositories(reset: Boolean = false) {
    if (reset) {
      page = 0
    }
    val pageNumParam = ++page
    sendEvent(StarredEvent.SetLoading(true))
    request({service.getMyStarred(pageNumParam)}, onSuccess = {
      sendEvent(StarredEvent.SetRepositoryList(it, pageNumParam))
    }, finally = {
      sendEvent(StarredEvent.SetLoading(false))
    })
  }
}