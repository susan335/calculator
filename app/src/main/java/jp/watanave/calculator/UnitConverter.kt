package jp.watanave.calculator

import android.content.Context
import android.util.TypedValue

/**
 * Created by Susan on 2016/01/25.
 */

public class UnitConverter {
    companion object {
        fun dp2px(dp : Double,  context : Context) : Double{
            val metrics = context.resources.displayMetrics
            return dp * metrics.density
        }
        fun px2dp(px : Double,  context : Context) : Double{
            val metrics = context.resources.displayMetrics
            return px / metrics.density
        }
        fun sp2px(sp : Double,  context : Context) : Double{
            val metrics = context.resources.displayMetrics
            return sp * metrics.scaledDensity
        }
        fun px2sp(px : Double,  context : Context) : Double{
            val metrics = context.resources.displayMetrics
            return px / metrics.scaledDensity
        }
    }
}