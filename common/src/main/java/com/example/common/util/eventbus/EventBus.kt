package com.example.common.util.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 14:21
 */
class EventBus @Inject constructor() {
  private val _events = MutableSharedFlow<AppEvent>()
  val events = _events.asSharedFlow()

  suspend fun invokeEvent(event: AppEvent) = _events.emit(event)
}