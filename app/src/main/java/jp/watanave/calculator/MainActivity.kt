package jp.watanave.calculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import jp.watanave.calculator.NumericButton
import java.util.*

class MainActivity : AppCompatActivity(), CalculatorContext {

    //Properties
    private var calculatorStatus: CalculatorStatus = CalculatorStatusEnterValueA()
    private val numericTextView: TextView by bindView(R.id.textview_numeric)
    private val operatorTextView : TextView by bindView(R.id.textview_operator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun clearCalculator() {
        this.calculatorStatus = CalculatorStatusEnterValueA()
        this.numericTextView.text = DEFAULT_STRING_VALUE
        this.operatorTextView.text = ""
        CalculateEngine.clear()
    }
    private fun callCalculateFunction(f : ()->Unit) {
        try {
            f()
        }
        catch (e : CalculateException) {
            e.message?.let {
                this.showDialog(it)
            }
            customLog(LogLevel.Debug, this.javaClass.name, "CalculateException: NumericString -> ${e.numericString}")
            this.clearCalculator()
        }
        catch (e : NumberFormatException) {
            // 起こらないハズ
            this.showDialog("想定外のエラーです。(NumberFormatException)")
            this.clearCalculator()
        }
        catch (e : Exception) {
            // 起こらないハズ
            this.showDialog("想定外のエラーです。(${e.javaClass.name})")
            this.clearCalculator()
        }
    }

    // Actions
    public fun onClickAllClear(sender: View?) {
        this.calculatorStatus.clear(this)
    }
    public fun onClickNumberButton(sender: View?) {
        // 入力系
        val numericButton = sender as? NumericButton ?: return

        this.callCalculateFunction({
            this.calculatorStatus.enterNumericValue(this, numericButton.numericValue)
        })
    }
    public fun onClickCalculateSubOperator(sender : View?) {
        val button = sender as? Button ?: return
        // 文字操作系
        fun calculationSubOperatorFromButton(button : Button) : CalculationSubOperator? {
            when (button) {
                this.findViewById(R.id.button_percent) -> return CalculationSubOperator.Percent
                this.findViewById(R.id.button_sign) -> return CalculationSubOperator.Sign
                this.findViewById(R.id.button_dot) -> return CalculationSubOperator.Period
                else -> return null
            }
        }
        val calculationSubOperator = calculationSubOperatorFromButton(button) ?: return

        this.callCalculateFunction({
            this.calculatorStatus.enterCalculationSubOperator(this, calculationSubOperator)
        })
    }
    public fun onClickCalculateOperator(sender : View?) {
        val button = sender as? Button ?: return

        fun calucateOperatorFromButton(button : Button) : CalculationOperator? {
            when (button) {
                this.findViewById(R.id.button_addtion) -> return CalculationOperator.Addition
                this.findViewById(R.id.button_subtraction) -> return CalculationOperator.Subtraction
                this.findViewById(R.id.button_multiplication) -> return CalculationOperator.Multiplication
                this.findViewById(R.id.button_division) -> return CalculationOperator.Division
                else -> return null
            }
        }
        val calculationOperator = calucateOperatorFromButton(button) ?: return

        this.callCalculateFunction({
            this.calculatorStatus.enterCalculationOperator(this, calculationOperator)
        })
    }
    public fun onClickEqual(sender : View?) {
        this.callCalculateFunction({
            this.calculatorStatus.enterEqual(this)
        })
    }

    override fun updateNumericValue(newValue: String) {
        this.numericTextView.text = newValue
    }
    override fun changeStatus(nextStatus: CalculatorStatus) {
        customLog(LogLevel.Debug, this.localClassName, "ChangeState: " + nextStatus.javaClass.name)
        this.calculatorStatus = nextStatus
    }
    override fun updateCalculationOperator(operator : CalculationOperator?) {
        this.operatorTextView.text = operator?.toString() ?: ""
    }
}
