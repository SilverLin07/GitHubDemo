package com.example.githubdemo.model

import com.example.common.base.UIEvent
import com.example.common.base.UIState
import com.example.githubdemo.data.bean.RepositoryItem


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 17:29
 */
sealed class SearchEvent: UIEvent {
  data class SetLoading(val loading: Boolean): SearchEvent()
  data class ShowRowFilterType(val showRowFilterType: Int): SearchEvent()
  data class SetTabLanguage(val tabLanguage: String): SearchEvent()
  data class SetTabSort(val tabSort: String): SearchEvent()
  data class SetRepositoryList(val repositoryList: List<RepositoryItem>, val page: Int): SearchEvent()
  data class SetShowIssues(val showIssues: Boolean): SearchEvent()
}
data class SearchState(val loading: Boolean = false,
                       val showRowFilterType: Int = -1,
                       val language: String = "Languages",
                       val sortBy: String = "Sort by",
                       val repositoryList: List<RepositoryItem> ?= null, val showIssues: Boolean = false): UIState