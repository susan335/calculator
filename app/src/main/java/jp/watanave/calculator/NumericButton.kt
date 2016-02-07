package jp.watanave.calculator

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import jp.watanave.calculator.R

/**
 * Created by Susan on 2016/01/24.
 */
public class NumericButton : Button {
    val numericValue: Int

    constructor(context: Context?, attrs : AttributeSet) : super(context, attrs) {
        this.numericValue = this.getNumericalValue(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.numericValue = this.getNumericalValue(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        this.numericValue = this.getNumericalValue(context, attrs)
    }

    private fun getNumericalValue(context: Context?, attrs: AttributeSet?) : Int {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.NumericButton)
        return typedArray?.getInt(R.styleable.NumericButton_numericValue, -1) ?: -1
    }
}
