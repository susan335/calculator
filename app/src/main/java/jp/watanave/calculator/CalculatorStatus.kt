package jp.watanave.calculator

/**
 * Created by Susan on 2016/01/30.
 */

interface CalculatorContext {
    fun changeStatus(nextStatus: CalculatorStatus)
    fun updateNumericValue(newValue : String)
    fun updateCalculationOperator(operator: CalculationOperator?)
}

abstract class CalculatorStatus(initialValue : String) {
    constructor() : this(DEFAULT_STRING_VALUE)

    var dirtyFlag = false
    var numericString : NumericString = NumericString(initialValue)


    open fun enterNumericValue(calucContext : CalculatorContext, numericValue : Int) {
        this.numericString.appendNumber(numericValue)
        calucContext.updateNumericValue(this.numericString.toString())
        this.dirtyFlag = true
    }

    open fun enterCalculationSubOperator(calucContext : CalculatorContext, subOperator : CalculationSubOperator) {
        when (subOperator) {
            CalculationSubOperator.Percent -> this.numericString.percent()
            CalculationSubOperator.Sign -> this.numericString.switchSign()
            CalculationSubOperator.Period-> this.numericString.appendPeriod()
        }
        calucContext.updateNumericValue(this.numericString.toString())
        this.dirtyFlag = true
    }

    abstract fun enterCalculationOperator(calucContext : CalculatorContext, operator: CalculationOperator)

    open fun enterEqual(calucContext : CalculatorContext) {
        this.dirtyFlag = true
    }

    open fun clear(calucContext : CalculatorContext) {
        if(!this.dirtyFlag) {
            CalculateEngine.clear()
            calucContext.updateCalculationOperator(null)
            calucContext.changeStatus(CalculatorStatusEnterValueA())
        }
        this.numericString = NumericString()
        calucContext.updateNumericValue(this.numericString.toString())
        this.dirtyFlag = false
    }
}

class CalculatorStatusEnterValueA(initialValue : String) : CalculatorStatus(initialValue) {
    constructor() : this(DEFAULT_STRING_VALUE)

    override fun enterCalculationOperator(calucContext : CalculatorContext, operator: CalculationOperator) {
        CalculateEngine.valueA = this.numericString.toBigDecimal()
        CalculateEngine.calculationOperator = operator

        calucContext.updateCalculationOperator(operator)
        calucContext.changeStatus(CalculatorStatusEnterValueB())
        this.dirtyFlag = true
    }

    override fun enterEqual(calucContext : CalculatorContext) {
    }
}


class CalculatorStatusEnterValueB(initialValue : String) : CalculatorStatus(initialValue){
    constructor() : this(DEFAULT_STRING_VALUE)

    override fun enterCalculationOperator(calucContext : CalculatorContext, operator: CalculationOperator) {
        if(!this.dirtyFlag) {
            CalculateEngine.calculationOperator = operator
            calucContext.updateCalculationOperator(operator)
            return;
        }

        CalculateEngine.valueB = this.numericString.toBigDecimal()
        val answer = CalculateEngine.calculate() ?: return
        calucContext.updateNumericValue(CalculatorStringFormatter.format(answer.toPlainString()))

        CalculateEngine.valueA = answer
        CalculateEngine.calculationOperator = operator
        calucContext.updateCalculationOperator(operator)
        this.numericString = NumericString()

        this.dirtyFlag = true
    }

    override fun enterEqual(calucContext : CalculatorContext) {
        if(!this.dirtyFlag) {
            return;
        }
        CalculateEngine.valueB = this.numericString.toBigDecimal()
        val answer = CalculateEngine.calculate() ?: return
        calucContext.updateNumericValue(CalculatorStringFormatter.format(answer.toString()))

        calucContext.changeStatus(CalculatorStatusCalculateFinish(answer.toString()))
        this.dirtyFlag = true
    }
}


class CalculatorStatusCalculateFinish(initialValue : String) : CalculatorStatus(initialValue) {
    constructor() : this(DEFAULT_STRING_VALUE)

    override fun enterNumericValue(calucContext : CalculatorContext, numericValue : Int) {
        CalculateEngine.clear()
        calucContext.updateCalculationOperator(null)
        val nextState = CalculatorStatusEnterValueA()
        calucContext.changeStatus(nextState)
        nextState.enterNumericValue(calucContext, numericValue)
        this.dirtyFlag = true
    }

    override fun enterCalculationSubOperator(calucContext : CalculatorContext, subOperator : CalculationSubOperator) {
        CalculateEngine.clear()
        calucContext.updateCalculationOperator(null)
        val nextState = CalculatorStatusEnterValueA()
        calucContext.changeStatus(nextState)
        nextState.enterCalculationSubOperator(calucContext, subOperator)
        this.dirtyFlag = true
    }

    override fun enterCalculationOperator(calucContext : CalculatorContext, operator: CalculationOperator) {
        CalculateEngine.valueA = this.numericString.toBigDecimal()
        CalculateEngine.calculationOperator = operator
        CalculateEngine.valueB = null
        calucContext.updateCalculationOperator(operator)
        calucContext.changeStatus(CalculatorStatusEnterValueB())
        this.dirtyFlag = true
    }

    override fun enterEqual(calucContext : CalculatorContext) {
        CalculateEngine.valueA = this.numericString.toBigDecimal()
        val answer = CalculateEngine.calculate() ?: return
        this.numericString = NumericString(answer)
        calucContext.updateNumericValue(this.numericString.toString())

        this.dirtyFlag = true
    }
}