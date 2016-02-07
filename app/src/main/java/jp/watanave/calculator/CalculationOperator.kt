
package jp.watanave.calculator

/**
 * Created by Susan on 2016/01/30.
 */

public enum class CalculationOperator {
    Addition {
        override fun toString() : String {
            return "+"
        }
    },
    Subtraction {
        override fun toString() : String {
            return "-"
        }
    },
    Multiplication {
        override fun toString() : String {
            return "×"
        }
    },
    Division {
        override fun toString() : String {
            return "÷"
        }
    }
}

enum class CalculationSubOperator {
    Percent,
    Sign,
    Period
}