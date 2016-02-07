package jp.watanave.calculator

/**
 * Created by Susan on 2016/01/31.
 */

class CalculateException(numericString : String)
    : Exception("このアプリで扱うことのできる数値の範囲を越えました。") {
    var numericString = numericString
}