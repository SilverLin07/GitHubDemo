package com.example.githubdemo

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.example.common.util.DataStoreUtils
import dagger.hilt.android.testing.HiltTestApplication


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-28 18:54
 */
class HiltTestRunner : AndroidJUnitRunner() {
  override fun newApplication(
    cl: ClassLoader?,
    className: String?,
    context: Context?,
  ): Application {
    return super.newApplication(cl, HiltTestApplication::class.java.name, context)
  }
}