package com.example.githubdemo.viewmodel

import com.example.common.base.BaseViewModel
import com.example.common.base.Reducer
import com.example.githubdemo.data.network.MainService
import com.example.githubdemo.model.ExploreEvent
import com.example.githubdemo.model.ExploreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 17:29
 */
@HiltViewModel
class ExploreViewModel @Inject constructor(val service: MainService): BaseViewModel<ExploreState, ExploreEvent>() {
  override val state: StateFlow<ExploreState>
    get() = reducer.state

  private val reducer = ExploreReducer(ExploreState())
  private fun sendEvent(event: ExploreEvent) {
    reducer.sendEvent(event)
  }
  class ExploreReducer(initVal: ExploreState): Reducer<ExploreState, ExploreEvent>(initVal) {
    override fun reduce(oldState: ExploreState, event: ExploreEvent) {

    }
  }

  // action
}