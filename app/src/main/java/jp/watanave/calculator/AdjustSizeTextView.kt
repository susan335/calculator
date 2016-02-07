package jp.watanave.calculator

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import jp.watanave.calculator.R
import jp.watanave.calculator.UnitConverter

/**
 * Created by Susan on 2016/01/25.
 */

public class AdjustSizeTextView : TextView {
    private val minTextSize: Float
    private val maxTextSize: Float
    private val paint: Paint? = Paint()

    companion object {
        val DEFAULT_MIN_TEXT_SIZE = 30.0f
        val DEFAULT_MAX_TEXT_SIZE = 80.0f
    }

    fun defaultMinTextSize() : Float {
        return UnitConverter.sp2px(DEFAULT_MIN_TEXT_SIZE.toDouble(), this.context).toFloat()
    }
    fun defaultMaxTextSize() : Float {
        return UnitConverter.sp2px(DEFAULT_MAX_TEXT_SIZE.toDouble(), this.context).toFloat()
    }

    constructor(context: Context?) : super(context) {
        this.minTextSize = defaultMinTextSize()
        this.maxTextSize = defaultMaxTextSize()
    }
    constructor(context: Context?, attrs : AttributeSet) : super(context, attrs) {
        this.minTextSize = this.getMinFontSizeFromAttr(context, attrs)
        this.maxTextSize = this.getMaxFontSizeFromAttr(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.minTextSize = this.getMinFontSizeFromAttr(context, attrs)
        this.maxTextSize = this.getMaxFontSizeFromAttr(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        this.minTextSize = this.getMinFontSizeFromAttr(context, attrs)
        this.maxTextSize = this.getMaxFontSizeFromAttr(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.getFitTextSize())
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.getFitTextSize())
    }

    private fun getTextWidth(textSize : Float) : Float {
        this.paint ?: return 0.0f

        this.paint.textSize = textSize
        return this.paint.measureText(this.text.toString())
    }
    private fun getFitTextSize() : Float{
        var textSize = this.maxTextSize
        while (this.measuredWidth < this.getTextWidth(textSize)) {
            --textSize;

            if(textSize <= this.minTextSize) {
                textSize = this.minTextSize
                break
            }
        }
        return textSize
    }

    private fun getTextSizeFromAttrs(context: Context?, attrs: AttributeSet?, attrsIndex : Int) : Float? {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.AdjustSizeTextView)
        val stringValue = typedArray?.getString(attrsIndex) ?: return null

        when {
            stringValue.endsWith("sp") -> {
                val stringSpValue = stringValue.dropLast("sp".length)
                return UnitConverter.sp2px(stringSpValue.toDouble(), this.context).toFloat()
            }
            stringValue.endsWith("dp") -> {
                val stringDpValue = stringValue.dropLast("dp".length)
                return UnitConverter.dp2px(stringDpValue.toDouble(), this.context).toFloat()
            }
            else -> {
                return stringValue.toFloat()
            }
        }
    }
    private fun getMinFontSizeFromAttr(context: Context?, attrs: AttributeSet?) : Float {
        val minFontSize = this.getTextSizeFromAttrs(context, attrs, R.styleable.AdjustSizeTextView_minTextSize)
        minFontSize?.let {
            return minFontSize
        }
        return defaultMinTextSize()
    }
    private fun getMaxFontSizeFromAttr(context: Context?, attrs: AttributeSet?) : Float {
        val maxFontSize = this.getTextSizeFromAttrs(context, attrs, R.styleable.AdjustSizeTextView_maxTextSize)
        maxFontSize?.let {
            return maxFontSize
        }
        return defaultMaxTextSize()
    }
}