package com.example.common.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.navigation.NavHostController

open class BaseApp: Application() {
  companion object {
    @SuppressLint("StaticFieldLeak")
    lateinit var navHostController: NavHostController
  }
}