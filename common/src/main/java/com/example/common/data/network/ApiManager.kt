package com.example.common.data.network

import com.example.common.data.constants.Constants
import com.example.common.data.constants.Constants.BASE_URL
import com.example.common.data.network.interceptor.HeaderInterceptor
import com.example.common.data.network.interceptor.LogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiManager {
  private lateinit var SERVICE: Retrofit

  // 提供Retrofit的方法，单例形式
  val retrofit: Retrofit
    get() {
      if (!ApiManager::SERVICE.isInitialized) {
        SERVICE = Retrofit.Builder().run {
          client(okHttp)
          addConverterFactory(GsonConverterFactory.create())
          baseUrl(BASE_URL)
          build()
        }
      }
      return SERVICE
    }

  // 提供okHttp的方法
  private val okHttp: OkHttpClient
    get() {
      return OkHttpClient.Builder().run {
        connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
        readTimeout(Constants.READ_TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(Constants.WRITE_TIME_OUT, TimeUnit.SECONDS)
        addInterceptor(HeaderInterceptor())
        addInterceptor(LogInterceptor())
        build()
      }
    }
}