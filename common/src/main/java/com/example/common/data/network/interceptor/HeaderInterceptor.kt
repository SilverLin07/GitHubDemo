package com.example.common.data.network.interceptor

import com.example.common.util.DataStoreUtils
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor: Interceptor {
  private val HEADER_AUTHOR = "Authorization"

  override fun intercept(chain: Interceptor.Chain): Response {
    val accessToken = "token ${DataStoreUtils.readStringData(DataStoreUtils.ACCESS_TOKEN, "")}"

    val chainBuilder = chain.request().newBuilder()
    with(chainBuilder) {
      // 添加token
      addHeader(HEADER_AUTHOR, accessToken)
    }
    return chain.proceed(chainBuilder.build())
  }
}