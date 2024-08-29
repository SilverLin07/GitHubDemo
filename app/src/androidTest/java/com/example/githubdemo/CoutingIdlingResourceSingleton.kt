package com.example.githubdemo

import androidx.test.espresso.idling.CountingIdlingResource


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-29 12:26
 */
object CountingIdlingResourceSingleton {

  private const val RESOURCE = "GLOBAL"

  @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

  fun increment() {
    countingIdlingResource.increment()
  }

  fun decrement() {
    if (!countingIdlingResource.isIdleNow) {
      countingIdlingResource.decrement()
    }
  }
}