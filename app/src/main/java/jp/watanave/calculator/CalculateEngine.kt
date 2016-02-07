package jp.watanave.calculator

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * Created by Susan on 2016/01/25.
 */

public object CalculateEngine : Observable() {
    public val NOTIFY_VALUE_A_CHANGED = "NOTIFY_LEFT_VALUE_CHANGED"
    public val NOTIFY_OPERATOR_CHANGED = "NOTIFY_OPERATOR_CHANGED"
    public val NOTIFY_VALUE_B_CHANGED = "NOTIFY_RIGHT_VALUE_CHANGED"

    public var valueA: BigDecimal? = null
        set(value) {
            field = value
            this.setChanged()
            this.notifyObservers(NOTIFY_VALUE_A_CHANGED)
        }
    public var valueB: BigDecimal? = null
        set(value) {
            field = value
            this.setChanged()
            this.notifyObservers(NOTIFY_VALUE_B_CHANGED)
        }
    public var calculationOperator: CalculationOperator? = null
        set(value) {
            field = value
            this.setChanged()
            this.notifyObservers(NOTIFY_OPERATOR_CHANGED)
        }

    public fun clear() {
        valueA = null
        valueB = null
        calculationOperator = null
    }

    public fun calculate() : BigDecimal? {
        val leftSideValue = this.valueA ?: return null
        val rightSideValue = this.valueB ?: return null
        val calcuOperator = this.calculationOperator ?: return null

        var answer : BigDecimal = when(calcuOperator) {
            CalculationOperator.Addition -> leftSideValue.add(rightSideValue)
            CalculationOperator.Subtraction -> leftSideValue.subtract(rightSideValue)
            CalculationOperator.Multiplication -> leftSideValue.multiply(rightSideValue)
            CalculationOperator.Division -> {
                if(rightSideValue == BigDecimal.ZERO) {
                    // 0除算対策
                    BigDecimal.ZERO
                }
                else {
                    customLog(LogLevel.Debug, this.javaClass.name, "${leftSideValue.scale()} : ${rightSideValue.scale()}")
                    try {
                        leftSideValue.divide(rightSideValue)
                    }
                    catch (e: ArithmeticException) {
                        // 循環少数対策
                        leftSideValue.divide(rightSideValue, 64, RoundingMode.HALF_UP)
                    }
                }
            }
        }

        return answer
    }
}