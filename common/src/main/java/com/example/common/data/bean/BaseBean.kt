package com.example.common.data.bean

data class BaseBean<T> (
  var code: Int,
  var data: T?,
  var message: String,
  var rel: Boolean,
  var status: Int,
  var timeStamp: String
)