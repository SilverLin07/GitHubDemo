package com.example.common.util

import java.util.Timer
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.concurrent.timerTask


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 18:21
 */
object FunctionUtil {
  private const val DEFAULT_DURATION_TIME = 1000L
  var timer: Timer? = null


  /**
   * 防抖函数
   */
  fun debounce(duration: Long = DEFAULT_DURATION_TIME, doThing: () -> Unit) {
    timer?.cancel()
    timer = Timer().apply {
      schedule(timerTask {
        doThing.invoke()
        timer = null
      }, duration)
    }
  }

  /**
   * 节流函数
   */
  var lastTimeMill = 0L
  fun throttle(duration: Long = DEFAULT_DURATION_TIME, continueCall: (() -> Unit)? = null, doThing: () -> Unit) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastTimeMill > duration) {
      doThing.invoke()
      lastTimeMill = System.currentTimeMillis()
    } else {
      continueCall?.invoke()
    }
  }

  //字符串是否全是数字
  fun isNumeric(str: String?): Boolean {
    val pattern: Pattern = Pattern.compile("[0-9]*")
    val isNum: Matcher = pattern.matcher(str)
    return isNum.matches()
  }
}
