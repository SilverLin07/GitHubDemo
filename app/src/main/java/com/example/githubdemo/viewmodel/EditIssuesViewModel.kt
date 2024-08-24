package com.example.githubdemo.viewmodel

import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.BaseViewModel
import com.example.common.base.Reducer
import com.example.githubdemo.data.bean.Issue
import com.example.githubdemo.data.network.MainService
import com.example.githubdemo.model.EditIssuesEvent
import com.example.githubdemo.model.EditIssuesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-24 13:23
 */
@HiltViewModel
class EditIssuesViewModel @Inject constructor(val service: MainService): BaseViewModel<EditIssuesState, EditIssuesEvent>() {
  override val state: StateFlow<EditIssuesState>
    get() = reducer.state
  private fun sendEvent(event: EditIssuesEvent) {
    reducer.sendEvent(event)
  }
  private val reducer = EditIssuesReducer(EditIssuesState())
  class EditIssuesReducer(initVal: EditIssuesState): Reducer<EditIssuesState, EditIssuesEvent>(initVal) {
    override fun reduce(oldState: EditIssuesState, event: EditIssuesEvent) {
      when (event) {
        is EditIssuesEvent.SetLoading -> {
          setState(oldState.copy(loading = event.loading))
        }
        is EditIssuesEvent.SetCommitEnable -> {
          setState(oldState.copy(commitEnable = event.commitEnable))
        }
      }
    }
  }

  // action
  fun checkCommitEnable(title: String, body: String) {
    sendEvent(EditIssuesEvent.SetCommitEnable(title.isNotBlank() && body.isNotBlank()))
  }
  fun createIssue(owner: String, repo: String, title: String, body: String) {
    val issue = Issue(title, body)
    request({service.createIssue(owner, repo, issue)}, onSuccess = {
      ToastUtils.showShort("Commit success")
    })
  }
}