package com.iandroid.allclass.lib_utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import androidx.annotation.WorkerThread

object DiskUtils {

    /**
     * return free disk space in MB
     * */
    @SuppressLint("UsableSpace")
    @JvmStatic
    fun getFreeSpace(context: Context): Long {
        val denominator = 1024 * 1024
        return context.externalCacheDir?.usableSpace?.div(denominator) ?: 0
    }

    @JvmStatic
    @WorkerThread
    fun canWrite() = try {
        Environment.getExternalStorageDirectory().canWrite()
    } catch (ignored: Exception) {
        false
    }
}