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
import com.example.githubdemo.model.MineEvent
import com.example.githubdemo.model.MineState
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
class MineViewModel @Inject constructor(val service: MainService, private val eventBus: EventBus): BaseViewModel<MineState, MineEvent>() {
  override val state: StateFlow<MineState>
    get() = reducer.state
  private val reducer = HomeReducer(MineState())
  private fun sendEvent(event: MineEvent) {
    reducer.sendEvent(event)
  }

  class HomeReducer(initVal: MineState): Reducer<MineState, MineEvent>(initVal) {
    override fun reduce(oldState: MineState, event: MineEvent) {
      when (event) {
        is MineEvent.SetLoading -> {
          setState(oldState.copy(loading = event.loading))
        }
        is MineEvent.SetShowSignIn -> {
          setState(oldState.copy(showSignIn = event.showSignIn))
        }
        is MineEvent.SetRepositoryList -> {
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
    viewModelScope.launch {
      eventBus.events.filter { it is AuthEvent }.collectLatest {
        getMyRepositories(true)
        sendEvent(MineEvent.SetShowSignIn(false))
      }
    }

    if (DataStoreUtils.readStringData(DataStoreUtils.ACCESS_TOKEN, "").isNotBlank()) {
      getMyRepositories(true)
    } else {
      sendEvent(MineEvent.SetShowSignIn(true))
    }
  }

  var page = 0
  // action
  fun launchAuthorizationUrl(context: Context) {
    val intent = CustomTabsIntent.Builder().build()
    intent.launchUrl(context, getAuthorizationUrl())
  }

  private fun getAuthorizationUrl(): Uri {
    return Uri.Builder().scheme("https")
      .authority("github.com")
      .appendPath("login")
      .appendPath("oauth")
      .appendPath("authorize")
      .appendQueryParameter("client_id", Constants.GITHUB_CLIENT_ID)
      .appendQueryParameter("redirect_uri", Constants.REDIRECT_URL)
      .appendQueryParameter("scope", "user,repo,gist,notifications,read:org")
      .appendQueryParameter("state", Constants.APPLICATION_ID)
      .build()
  }

  fun getMyRepositories(reset: Boolean = false) {
    if (reset) {
      page = 0
    }
    val pageNumParam = ++page
    sendEvent(MineEvent.SetLoading(true))
    request({service.getMyRepos(pageNumParam)}, onSuccess = {
      sendEvent(MineEvent.SetRepositoryList(it, pageNumParam))
    }, finally = {
      sendEvent(MineEvent.SetLoading(false))
    })
  }
}