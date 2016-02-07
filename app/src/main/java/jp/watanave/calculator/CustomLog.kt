package jp.watanave.calculator

import android.util.Log

/**
 * Created by Susan on 2016/01/25.
 */

enum class LogLevel {
    Debug,
    Information,
    Warning,
    Error
}

fun customLog(logLevel: LogLevel, tag : String, message : String) {
//    if (!BuildConfig.DEBUG) {
//        return;
//    }
    when (logLevel) {
        LogLevel.Debug -> Log.d(tag, message)
        LogLevel.Information -> Log.i(tag, message)
        LogLevel.Warning -> Log.w(tag, message)
        LogLevel.Error -> Log.e(tag, message)
    }
}