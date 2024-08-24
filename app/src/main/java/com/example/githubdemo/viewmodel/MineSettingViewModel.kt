package com.example.githubdemo.viewmodel

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LanguageUtils
import com.example.common.base.BaseViewModel
import com.example.common.base.Reducer
import com.example.common.ext.findActivity
import com.example.common.util.DataStoreUtils
import com.example.githubdemo.data.network.MainService
import com.example.githubdemo.model.MineSettingEvent
import com.example.githubdemo.model.MineSettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale
import javax.inject.Inject


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 20:56
 */
@HiltViewModel
class MineSettingViewModel @Inject constructor(val service: MainService): BaseViewModel<MineSettingState, MineSettingEvent>() {
  override val state: StateFlow<MineSettingState>
    get() = reducer.state
  private val reducer = MineSettingReducer(MineSettingState())
  private fun sendEvent(event: MineSettingEvent) {
    reducer.sendEvent(event)
  }
  class MineSettingReducer(initVal: MineSettingState): Reducer<MineSettingState, MineSettingEvent>(initVal) {
    override fun reduce(oldState: MineSettingState, event: MineSettingEvent) {
      when (event) {
        is MineSettingEvent.SetLoading -> {
          setState(oldState.copy(loading = event.loading))
        }
        is MineSettingEvent.SetUserInfo -> {
          setState(oldState.copy(userInfoBean = event.userInfoBean))
        }
      }
    }
  }

  init {
    getUserInfo()
  }

  // action
  private fun getUserInfo() {
    sendEvent(MineSettingEvent.SetLoading(true))
    request({service.getUserInfo()}, onSuccess = {
      sendEvent(MineSettingEvent.SetUserInfo(it))
    }, finally = {
      sendEvent(MineSettingEvent.SetLoading(false))
    })
  }

  fun updateLocale(context: Context) {
    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    if (getCurrentLocale(configuration).language == "en") {
      LanguageUtils.applyLanguage(Locale("zh"))
    } else {
      LanguageUtils.applyLanguage(Locale("en"))
    }
  }

  private fun getCurrentLocale(configuration: Configuration): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      configuration.locales.get(0)
    } else {
      configuration.locale
    }
  }

  fun logOut() {
    DataStoreUtils.saveSyncStringData(DataStoreUtils.ACCESS_TOKEN, "")
  }
}