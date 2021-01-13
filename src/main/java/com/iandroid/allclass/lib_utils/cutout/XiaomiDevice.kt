package com.iandroid.allclass.lib_utils.cutout

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Rect
import android.os.Build

class XiaomiDevice : ICutoutDevice {

    @SuppressLint("PrivateApi")
    override fun hasNotch(activity: Activity?): Boolean {
        try {
            val properties = Class.forName("android.os.SystemProperties")
            val getMethod = properties.getMethod("getInt", String::class.java, Int::class.javaPrimitiveType)
            val value = getMethod.invoke(properties, "ro.miui.notch", 0) as Int
            return value == 1
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    override fun getSafeRect(context: Activity?): Rect {
        val result = context?.let {
            val resourceId = it.resources?.getIdentifier("notch_height", "dimen", "android") ?: 0
            it.resources.getDimensionPixelSize(resourceId)
        }
        return Rect(0, result ?: 0, 0, 0)
    }

    companion object {
        fun isApply(): Boolean {
            return "xiaomi".equals(Build.MANUFACTURER, true)
        }
    }
}