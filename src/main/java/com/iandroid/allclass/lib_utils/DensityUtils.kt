package com.iandroid.allclass.lib_utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

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

    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val realDisplayMetrics = DisplayMetrics()
        display.getRealMetrics(realDisplayMetrics)
        return realDisplayMetrics.widthPixels
    }
}