package com.iandroid.allclass.lib_utils.cutout;

import android.app.Activity;
import android.graphics.Rect;

public class CutoutHelper {
    private static ICutoutDevice mICutoutDevice;

    static {
        if (AndroidPDevice.isApply()) {
            mICutoutDevice = new AndroidPDevice();
        } else if (OppoDevice.isApply()) {
            mICutoutDevice = new OppoDevice();
        } else if (SamsungDevice.isApply()) {
            mICutoutDevice = new SamsungDevice();
        } else if (XiaomiDevice.Companion.isApply()) {
            mICutoutDevice = new XiaomiDevice();
        } else {
            mICutoutDevice = new DefaultDevice();
        }
    }

    private CutoutHelper() {
    }

    /**
     * 是否有缺口
     */
    public static boolean hasNotch(Activity activity) {
        return mICutoutDevice.hasNotch(activity);
    }

    /**
     * 回傳安全的區域,Rect(0,0,0,0)表示上下左右的區域都是安全的
     */
    public static Rect getSafeRect(Activity context) {
        return mICutoutDevice.getSafeRect(context);
    }
}

