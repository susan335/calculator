package jp.watanave.calculator

/**
 * Created by Susan on 2016/01/25.
 */

val SIGN_STRING= "-"
val DEFAULT_STRING_VALUE = "0"
val MAX_NUMBER_OF_DIGITS = 15
val MAX_LENGTH_OF_NUM_STRING = 200
val DECIMAL_POINT = '.'

infix fun CharSequence.containChar(value : Char) : Boolean {
    this.find {
        it.equals(value)
    }?.let {
        return true
    }
    return false
}
