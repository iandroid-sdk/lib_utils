package com.iandroid.allclass.lib_utils

import android.content.Context
import android.util.TypedValue

/**
created by wangkm
on 2020/9/16.
 */
object DensityUtils {
    /**
     * dpתpx
     *
     * @param context
     * @return
     */
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, context.resources.displayMetrics
        ).toInt()
    }
}