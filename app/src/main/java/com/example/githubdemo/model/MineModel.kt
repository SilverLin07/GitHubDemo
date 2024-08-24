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
sealed class MineEvent: UIEvent {
  data class SetLoading(val loading: Boolean): MineEvent()
  data class SetShowSignIn(val showSignIn: Boolean): MineEvent()
  data class SetRepositoryList(val repositoryList: List<RepositoryItem>, val page: Int): MineEvent()
}

data class MineState(val loading: Boolean = false, val showSignIn: Boolean = false,  val repositoryList: List<RepositoryItem> ?= null): UIState