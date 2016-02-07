package jp.watanave.calculator

import java.math.BigDecimal

/**
 * Created by Susan on 2016/01/31.
 */
class NumericString {
    constructor() {
        this.stringValue = DEFAULT_STRING_VALUE
    }
    constructor(bigDecimal : BigDecimal) {
        this.stringValue = bigDecimal.toPlainString()
    }
    constructor(stringValue : String) {
        this.stringValue = stringValue
    }

    lateinit private var stringValue : String

    fun containDecimalPoint() : Boolean {
        this.stringValue.find {
            it.equals('.')
        }?.let {
            return true
        }
        return false
    }

    fun appendNumber(value : Int) {
        if(this.stringValue == DEFAULT_STRING_VALUE ||                     // 0
           this.stringValue == SIGN_STRING + DEFAULT_STRING_VALUE) {       // -0
            this.stringValue = value.toString()
            return
        }

        this.stringValue = this.stringValue + value.toString()
        if(this.stringValue.length > MAX_LENGTH_OF_NUM_STRING) {
            throw CalculateException(this.stringValue)
        }
    }

    fun appendPeriod() {
        if(this.containDecimalPoint()) {
            return
        }
        if(CalculatorStringFormatter.format(this.stringValue).contains('E')) {
            return
        }

        this.stringValue = this.stringValue + DECIMAL_POINT
    }

    fun switchSign() {
        if(this.stringValue.startsWith(SIGN_STRING)) {
            this.stringValue = this.stringValue.drop(SIGN_STRING.length)
        }
        else {
            this.stringValue = SIGN_STRING + this.stringValue
        }
    }

    fun percent() {
        if(this.stringValue.toDouble() == 0.0) {
            return
        }
        val bigDecimal = BigDecimal(this.stringValue)
        this.stringValue = bigDecimal.movePointLeft(2).toPlainString()

        if(this.stringValue.length > MAX_LENGTH_OF_NUM_STRING) {
            throw CalculateException(this.stringValue)
        }
    }

    fun toBigDecimal() : BigDecimal {
        return BigDecimal(this.stringValue)
    }

    override fun toString() : String {
        return CalculatorStringFormatter.format(this.stringValue)
    }
}