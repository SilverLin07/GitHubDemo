package com.example.githubdemo.model

import com.example.common.base.UIEvent
import com.example.common.base.UIState
import com.example.githubdemo.data.bean.RepositoryItem


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:45
 */
sealed class StarredEvent: UIEvent {
  data class SetLoading(val loading: Boolean): StarredEvent()
  data class SetRepositoryList(val repositoryList: List<RepositoryItem>, val page: Int): StarredEvent()
}

data class StarredState(val loading: Boolean = false,  val repositoryList: List<RepositoryItem> ?= null): UIState