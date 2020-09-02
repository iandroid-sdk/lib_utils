package com.iandroid.allclass.lib_utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

/**
 * Created by david on 2020/9/2.
 */
object PermissionUtils {
    // 狀態碼、標誌位
    private val REQUEST_STATUS_CODE = 0x001

    private val PERMISSIONS_VOICE_RTC_GROUP = arrayOf(
            Manifest.permission.RECORD_AUDIO
    )

    /**
     * 對權限字符串數組中的所有權限進行申請授權，如果用戶選擇了「never ask again」，則不會彈出系統的Permission申請授權對話框
     */
    fun requestPermissions(activity: Activity?, permissions: Array<String>?) {
        if (activity == null) {
            return
        }
        if (permissions == null) {
            return
        }
        ActivityCompat.requestPermissions(activity, permissions,
            REQUEST_STATUS_CODE
        )
    }

    fun checkSelfPermissionGroup(activity: Activity?, permissions: Array<String>?): Int {
        if (activity == null) {
            return PackageManager.PERMISSION_DENIED
        }
        if (permissions == null || permissions.isEmpty()) {
            return PackageManager.PERMISSION_DENIED
        }
        for (permission in permissions) {
            /**
             * hockey app reported.
             * java.lang.RuntimeException: Unknown exception code: 1 msg null
             * Package: com.lang.lang
             * Version Code: 479
             * Version Name: 2.8.1.2
             * Android: 4.4.4
             * Android Build: K30-T_S043_150212
             * Manufacturer: LENOVO
             * Model: Lenovo K30-T
             */
            try {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return PackageManager.PERMISSION_DENIED
                }
            } catch (e: Exception) {
                return PackageManager.PERMISSION_DENIED
            }
        }
        return PackageManager.PERMISSION_GRANTED
    }


    fun checkAndRequestRTCPermission(activity: Activity?): Boolean {
        return if (checkSelfPermissionGroup(
                activity,
                PERMISSIONS_VOICE_RTC_GROUP
            ) ==
                PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            requestPermissions(
                activity,
                PERMISSIONS_VOICE_RTC_GROUP
            )
            false
        }
    }
}