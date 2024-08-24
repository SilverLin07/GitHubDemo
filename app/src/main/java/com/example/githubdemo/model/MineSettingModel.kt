package com.example.githubdemo.model

import com.example.common.base.UIEvent
import com.example.common.base.UIState
import com.example.githubdemo.data.bean.UserInfoBean


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 20:55
 */
sealed class MineSettingEvent: UIEvent {
  data class SetLoading(val loading: Boolean): MineSettingEvent()
  data class SetUserInfo(val userInfoBean: UserInfoBean): MineSettingEvent()
}

data class MineSettingState(val loading: Boolean = true, val userInfoBean: UserInfoBean? = null): UIState