package com.iandroid.allclass.lib_utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import java.util.*

/**
 * Created by david on 2020/9/2.
 */
object PermissionUtils {
    // 狀態碼、標誌位
    private const val REQUEST_STATUS_CODE = 0x001
    const val REQUEST_START_VOICE_LIVE_CODE = 0x002
    const val REQUEST_ALBUM_CODE = 0x004    //访问相册权限
    const val REQUEST_CAMERA_CODE = 0x008   //拍照权限
    const val REQUEST_CALENDAR_CODE = 0X010 //日历权限
    const val REQUEST_READWIRTE_CODE = 0X020 //读写权限


    //连麦权限
    private val PERMISSIONS_VOICE_RTC_GROUP = arrayOf(Manifest.permission.RECORD_AUDIO)

    //开播权限
    private val PERMISSIONS_VOICE_LIVE_GROUP = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    //拍照权限
    private val PERMISSIONS_CAMERA_GROUP = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    //读写权限
    private val PERMISSIONS_READ_WRITE_STORAGE_GROUP = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    public var PERMISSIONS_CALENDAR_GROUP = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR
    )

    /**
     * 對權限字符串數組中的所有權限進行申請授權，如果用戶選擇了「never ask again」，則不會彈出系統的Permission申請授權對話框
     */
    private fun requestPermissions(
        @NonNull activity: Activity,
        @NonNull permissions: Array<String>
    ) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_STATUS_CODE)
    }

    private fun requestPermissions(
        @NonNull activity: Activity, @NonNull permissions: Array<String>, requestCode: Int
    ) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    public fun checkSelfPermissionGroup(
        @NonNull activity: Activity?,
        @NonNull permissions: Array<String>
    ): Int {
        for (permission in permissions) {
            try {
                activity?.run {
                    if (ActivityCompat.checkSelfPermission(this,
                            permission) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return PackageManager.PERMISSION_DENIED
                    }
                }
            } catch (e: Exception) {
                return PackageManager.PERMISSION_DENIED
            }
        }
        return PackageManager.PERMISSION_GRANTED
    }

    //检查日历权限
    fun requestCalendarPermission(activity: Activity?) {
        if (activity == null) return
        if (checkSelfPermissionGroup(activity,
                PERMISSIONS_CALENDAR_GROUP) == PackageManager.PERMISSION_GRANTED
        ) {
            //for activity callback
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.onRequestPermissionsResult(REQUEST_CALENDAR_CODE,
                    PERMISSIONS_CALENDAR_GROUP,
                    genGrantedResultArray(PERMISSIONS_CALENDAR_GROUP.size)!!)
            } else {
                requestPermissions(activity, PERMISSIONS_CALENDAR_GROUP, REQUEST_CALENDAR_CODE)
            }
        } else {
            requestPermissions(activity, PERMISSIONS_CALENDAR_GROUP, REQUEST_CALENDAR_CODE)
        }
    }

    /**
     * 检查连麦系统权限
     */
    fun checkAndRequestRTCPermission(activity: Activity): Boolean {
        return if (checkSelfPermissionGroup(activity,
                PERMISSIONS_VOICE_RTC_GROUP) == PackageManager.PERMISSION_GRANTED
        ) true
        else {
            requestPermissions(activity, PERMISSIONS_VOICE_RTC_GROUP)
            false
        }
    }

    /**
     * 检查开播权限
     */
    fun requestStartVoiceLivePermissions(activity: Activity) {
        if (checkSelfPermissionGroup(activity,
                PERMISSIONS_VOICE_LIVE_GROUP) == PackageManager.PERMISSION_GRANTED
        ) {
            //for activity callback
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                genGrantedResultArray(PERMISSIONS_VOICE_LIVE_GROUP.size)?.let {
                    activity.onRequestPermissionsResult(REQUEST_START_VOICE_LIVE_CODE,
                        PERMISSIONS_VOICE_LIVE_GROUP,
                        it)
                }
            } else {
                requestPermissions(activity,
                    PERMISSIONS_VOICE_LIVE_GROUP,
                    REQUEST_START_VOICE_LIVE_CODE)
            }
        } else {
            requestPermissions(activity,
                PERMISSIONS_VOICE_LIVE_GROUP,
                REQUEST_START_VOICE_LIVE_CODE)
        }
    }

    //拍照全选
    open fun requestCameraPermissions(activity: Activity, code: Int) {
        if (checkSelfPermissionGroup(activity,
                PERMISSIONS_CAMERA_GROUP) == PackageManager.PERMISSION_GRANTED
        ) {
            //for activity callback
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                genGrantedResultArray(PERMISSIONS_CAMERA_GROUP.size)?.let {
                    activity.onRequestPermissionsResult(code, PERMISSIONS_CAMERA_GROUP, it)
                }
            } else {
                requestPermissions(activity, PERMISSIONS_CAMERA_GROUP, code)
            }
        } else {
            requestPermissions(activity, PERMISSIONS_CAMERA_GROUP, code)
        }
    }

    //读写全选
    open fun requestReadWritePermissions(activity: Activity, code: Int) {
        if (checkSelfPermissionGroup(activity,
                PERMISSIONS_READ_WRITE_STORAGE_GROUP) == PackageManager.PERMISSION_GRANTED
        ) {
            //for activity callback
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                genGrantedResultArray(PERMISSIONS_READ_WRITE_STORAGE_GROUP.size)?.let {
                    activity.onRequestPermissionsResult(code,
                        PERMISSIONS_READ_WRITE_STORAGE_GROUP,
                        it)
                }
            } else {
                requestPermissions(activity, PERMISSIONS_READ_WRITE_STORAGE_GROUP, code)
            }
        } else {
            requestPermissions(activity, PERMISSIONS_READ_WRITE_STORAGE_GROUP, code)
        }
    }

    private fun genGrantedResultArray(size: Int): IntArray? {
        val grantResults = IntArray(size)
        Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED)
        return grantResults
    }
}