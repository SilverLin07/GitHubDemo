package com.example.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.common.data.bean.BaseBean
import com.example.common.data.constants.Router
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

abstract class BaseViewModel<S : UIState, in E : UIEvent> : ViewModel() {
  abstract val state: Flow<S>

  // 封装请求，通过传递各个函数参数进行处理
  fun <T> request(
    request: suspend () -> T,
    onSuccess: (data: T) -> Unit = {},
    onSuccessAny: () -> Unit = {},
    onFail: (code: Int, status: Int, msg: String, data: T?) -> Unit = { _, _, _, _ -> },
    finally: () -> Unit = {},
    showToast: Boolean = true
  ) {
    viewModelScope.launch {
      flow {
        emit(request())
      }
        .onEach {
          onSuccess(it)
          finally()
        }
        .catch {
          it.message?.let { exception ->
            onFail(-1, 0, exception, null)
            finally()
            LogUtils.e("request exception", exception)
            if (showToast) {
              ToastUtils.showShort(exception)
            }
          }
        }
        .collect()
    }
  }

  fun getMultipartBody(file: File): MultipartBody.Part {
    val fileName: String = file.name
    val split = fileName.split("[.]").toTypedArray()
    var str = ""
    if (split.size >= 2) {
      str = split[1]
    }
    var requestBody: RequestBody = when (str) {
      "jpeg" -> {
        file.asRequestBody("image/jpeg".toMediaType())
      }
      "png" -> {
        file.asRequestBody("image/png".toMediaType())
      }
      "mp4", "MP4" -> {
        file.asRequestBody("multipart/form-data".toMediaType())
      }
      else -> {
        file.asRequestBody("image/jpeg".toMediaType())
      }
    }
    return MultipartBody.Part.createFormData("file", fileName, requestBody)
  }
}