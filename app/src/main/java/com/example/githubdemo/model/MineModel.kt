package com.example.githubdemo.model

import com.example.common.base.UIEvent
import com.example.common.base.UIState


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:45
 */
sealed class MineEvent: UIEvent

data class MineState(val loading: Boolean = false): UIState