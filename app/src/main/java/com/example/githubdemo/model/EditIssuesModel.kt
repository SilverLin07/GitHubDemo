package com.example.githubdemo.model

import com.example.common.base.UIEvent
import com.example.common.base.UIState


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-24 13:23
 */
sealed class EditIssuesEvent: UIEvent {
  data class SetLoading(val loading: Boolean): EditIssuesEvent()
  data class SetCommitEnable(val commitEnable: Boolean): EditIssuesEvent()
}

data class EditIssuesState(val loading: Boolean = false, val commitEnable: Boolean = false): UIState