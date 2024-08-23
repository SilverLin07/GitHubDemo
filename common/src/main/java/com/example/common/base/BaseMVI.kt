package com.example.common.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UIState, E : UIEvent>(initVal: S) {
  private val _state: MutableStateFlow<S> = MutableStateFlow(initVal)
  val state: StateFlow<S>
    get() = _state

  fun sendEvent(event: E) {
    reduce(_state.value, event)
  }

  fun setState(newState: S) {
    _state.tryEmit(newState)
  }

  // 根据事件类型，将其转化为State
  abstract fun reduce(oldState: S, event: E)
}

interface UIState
interface UIEvent