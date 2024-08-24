package com.example.githubdemo.viewmodel

import com.example.common.base.BaseViewModel
import com.example.common.base.Reducer
import com.example.githubdemo.data.network.MainService
import com.example.githubdemo.model.MineEvent
import com.example.githubdemo.model.SearchEvent
import com.example.githubdemo.model.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 17:29
 */
@HiltViewModel
class SearchViewModel @Inject constructor(val service: MainService): BaseViewModel<SearchState, SearchEvent>() {
  override val state: StateFlow<SearchState>
    get() = reducer.state

  private val reducer = SearchReducer(SearchState())
  private fun sendEvent(event: SearchEvent) {
    reducer.sendEvent(event)
  }
  class SearchReducer(initVal: SearchState): Reducer<SearchState, SearchEvent>(initVal) {
    override fun reduce(oldState: SearchState, event: SearchEvent) {
      when (event) {
        is SearchEvent.SetLoading -> {
          setState(oldState.copy(loading = event.loading))
        }
        is SearchEvent.ShowRowFilterType -> {
          setState(oldState.copy(showRowFilterType = event.showRowFilterType))
        }
        is SearchEvent.SetTabLanguage -> {
          setState(oldState.copy(language = event.tabLanguage))
        }
        is SearchEvent.SetTabSort -> {
          setState(oldState.copy(sortBy = event.tabSort))
        }
        is SearchEvent.SetRepositoryList -> {
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

  var page = 0
  // action
  fun search(keyword: String, language: String = "", sort: String = "", reset: Boolean = false, callBack: () -> Unit = {}) {
    if (keyword.isBlank()) return

    sendEvent(SearchEvent.SetLoading(true))
    if (reset) {
      page = 0
    }
    val pageNumParam = ++page
    val sortStr = when (sort) {
      "Best match" -> ""
      "Most stars" -> "stars"
      "Most forks" -> "forks"
      "Recently updated" -> "updated"
      else -> ""
    }
    val query = if (language == "Languages") {
      keyword
    } else {
      "$keyword language:$language"
    }
    request({service.searchRepositories(query, sort = sortStr, page = pageNumParam)}, onSuccess = {
      sendEvent(SearchEvent.SetRepositoryList(it.items, pageNumParam))
      callBack()
    }, finally = {
      sendEvent(SearchEvent.SetLoading(false))
    })
  }

  fun showRowFilterType(showRowFilterType: Int) {
    sendEvent(SearchEvent.ShowRowFilterType(showRowFilterType))
  }

  fun setTabLanguage(tabLanguage: String) {
    sendEvent(SearchEvent.SetTabLanguage(tabLanguage))
  }

  fun setTabSort(tabSort: String) {
    sendEvent(SearchEvent.SetTabSort(tabSort))
  }
}