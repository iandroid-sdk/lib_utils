package com.iandroid.allclass.lib_utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
created by wangkm
on 2020/9/11.
 */
object DirectoryUtils {
    /**
     * 是否有存储写权限
     */
    private fun hasExternalStoragePermission(context: Context): Boolean {
        return context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 获取目录名字
     */
    fun getFilesDirectory(context: Context, dirName: String, preferExternal: Boolean = true): File {
        var dirShortName = dirName ?: ""

        var file: File? = null
        if (preferExternal
            && "mounted" == Environment.getExternalStorageState()
            && hasExternalStoragePermission(context)) {
            file = context.getExternalFilesDir(null)
        }

        if (file == null) {
            file = context.filesDir ?: File("/data/data/" + context.packageName + "/files/")
        }

        var pathName = file?.absolutePath
        var restult = pathName.run {
            var rtnPathname = this
            if (!endsWith("/")) {
                rtnPathname = "$rtnPathname/"
            }
            if (!TextUtils.isEmpty(dirShortName)) {
                rtnPathname = "$rtnPathname$dirShortName/"
            }
            rtnPathname
        }
        return File(mkDirs(restult))
    }

    /**
     * 创建目录
     */
    fun mkDirs(dirPath: String?): String? {
        return dirPath?.apply {
            var file = File(this)
            if (!file?.exists()) file.mkdirs()
        }
    }

    /**
     * 创建目录
     */
    fun mkDirs(file: File?): File? {
        return file?.apply {
            if (!exists()) mkdirs()
        }
    }

    fun getRandomFileName(ext: String): String {
        return getTimeFileName(System.currentTimeMillis(),
            "yyMMddHHmmssSSS") + ext
    }

    fun getTimeFileName(time: Long, type: String?): String {
        val sDateTime: String
        val sdf = SimpleDateFormat(type, Locale.getDefault())
        val dt = Date(time)
        sDateTime = sdf.format(dt)
        return sDateTime
    }

}