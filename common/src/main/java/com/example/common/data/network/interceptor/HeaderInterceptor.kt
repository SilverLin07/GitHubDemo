package com.example.common.data.network.interceptor

import com.example.common.util.DataStoreUtils
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor: Interceptor {
  private val HEADER_AUTHOR = "Authorization"

  override fun intercept(chain: Interceptor.Chain): Response {
    val chainBuilder = chain.request().newBuilder()
    with(chainBuilder) {
      val accessToken = DataStoreUtils.readStringData(DataStoreUtils.ACCESS_TOKEN, "")
      if (accessToken.isNotBlank()) {
        addHeader(HEADER_AUTHOR, "token $accessToken")
      }
    }
    return chain.proceed(chainBuilder.build())
  }
}