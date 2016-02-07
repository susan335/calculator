package jp.watanave.calculator

import java.text.DecimalFormat

/**
 * Created by Susan on 2016/01/24.
 */

public class CalculatorStringFormatter {
    companion object {
        fun format(stringValue : String) : String {
            if(stringValue.length > MAX_LENGTH_OF_NUM_STRING) {
                throw CalculateException(stringValue)
            }

            val decimalFormat = DecimalFormat.getNumberInstance()
            if(stringValue.containChar(DECIMAL_POINT)) {
                var decimalPointIndex = stringValue.indexOf(DECIMAL_POINT)

                when (decimalPointIndex) {
                    (stringValue.length - DECIMAL_POINT.toString().length) ->
                        decimalFormat.minimumFractionDigits = 1
                    else ->
                        decimalFormat.minimumFractionDigits = stringValue.length - decimalPointIndex - DECIMAL_POINT.toString().length
                }
            }

            var formattedString = decimalFormat.format(stringValue.toDouble())

            if(formattedString.length > MAX_NUMBER_OF_DIGITS) {
                val exponentFormat = DecimalFormat("0.0###############E0")
                formattedString = exponentFormat.format(stringValue.toDouble())
            }

            if(formattedString.endsWith("E0")) {
                formattedString = formattedString.dropLast("E0".length)
            }
            return formattedString
        }
    }
}