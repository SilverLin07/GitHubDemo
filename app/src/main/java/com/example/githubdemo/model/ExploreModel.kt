package com.example.githubdemo.model

import com.example.common.base.UIEvent
import com.example.common.base.UIState


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 17:29
 */
sealed class ExploreEvent: UIEvent
data class ExploreState(val loading: Boolean = true): UIState