package com.example.githubdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.util.DataStoreUtils
import com.example.common.util.eventbus.AuthEvent
import com.example.common.util.eventbus.EventBus
import com.example.githubdemo.data.network.MainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 14:19
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val service: MainService, private val eventBus: EventBus): ViewModel() {
  fun auth(code: String,
           clientId: String,
           clientSecret: String,
           redirectUrl: String) {
    val client = OkHttpClient()

    val formBody: RequestBody = FormBody.Builder()
      .add("client_id", clientId)
      .add("client_secret", clientSecret)
      .add("code", code)
      .add("redirect_uri", redirectUrl)
      .build()

    val request: Request = Request.Builder()
      .url("https://github.com/login/oauth/access_token")
      .post(formBody)
      .addHeader("Accept", "application/json")
      .build()

    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
      }

      override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful) {
          val responseBody: String = response.body!!.string()
          // 解析 JSON 获取访问令牌
          val accessToken = JSONObject(responseBody).getString("access_token")
          // 现在你可以用访问令牌来访问 GitHub API
          DataStoreUtils.saveSyncStringData(DataStoreUtils.ACCESS_TOKEN, accessToken)

          viewModelScope.launch {
            eventBus.invokeEvent(AuthEvent())
          }
        }
      }
    })
  }
}