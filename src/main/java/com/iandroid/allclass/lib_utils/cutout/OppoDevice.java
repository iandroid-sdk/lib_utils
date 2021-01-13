package com.iandroid.allclass.lib_utils.cutout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;

import com.iandroid.allclass.lib_utils.DensityUtils;

import java.lang.reflect.Method;

/**
 * Oppo device notch info
 * reference:
 * https://open.oppomobile.com/wiki/doc#id=10159
 * https://open.oppomobile.com/wiki/doc#id=10293
 * *
 */
public class OppoDevice implements ICutoutDevice {
    private static final String TAG = "OppoDevice";
    private String FEATURE_NOTCH = "com.oppo.feature.screen.heteromorphism";
    private String PROP_NOTCH = "ro.oppo.screen.heteromorphism";

    public static boolean isApply() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 &&
            Build.BRAND.toLowerCase().contains("oppo");
    }

    @Override
    public boolean hasNotch(Activity activity) {
        try {
            boolean isNotchEnable = activity.getPackageManager().hasSystemFeature(FEATURE_NOTCH);
            Log.i(TAG, "OPPO hardware enable: " + isNotchEnable);
            return isNotchEnable;
        } catch (Exception ignore) {
            return false;
        }
    }

    @Override
    public Rect getSafeRect(Activity activity) {
        if (hasNotch(activity)) {
            return internalGetSafeRect(activity);
        }
        return new Rect(0, 0, 0, 0);
    }

    private Rect internalGetSafeRect(Activity activity) {
        //expect get the prop is like "229,0:492,53" in model OPPO CPH 1851
        String str = getSystemProp(PROP_NOTCH);
        if (str != null) {
            String[] nums = str.split(",|:");
            try {
                if (nums.length == 4)
                    return new Rect(0, Integer.parseInt(nums[3]), 0, 0);
            } catch (NumberFormatException e) {
                Log.e(TAG, "invalid format OPPO notch param is " + str);
                return new Rect(0, getStatusBarHeight(activity), 0, 0);
            }
        }
        return new Rect(0, 0, 0, 0);
    }

    /**
     * after 9.0 reflection SystemProperties will fail cause the private
     */
    private String getSystemProp(String propString) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method mtd = clz.getMethod("get", String.class);
            return (String) mtd.invoke(null, propString);
        } catch (Exception e) {
            return null;
        }
    }

    private int getStatusBarHeight(Context context) {
        return DensityUtils.getStatusBarHeight(context);
    }
}
