package com.iandroid.allclass.lib_utils.cutout;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.Window;

public class SamsungDevice implements ICutoutDevice {
    private static final String TAG = "AndroidPDevice";

    public static boolean isApply() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
            Build.BRAND.toLowerCase().contains("samsung");
    }

    @Override
    public boolean hasNotch(Activity activity) {
        Window window = activity.getWindow();
        if (window == null) {
            return false;
        }
        boolean isNotchScreen = false;
        try {
            final Resources res = window.getContext().getResources();
            final int resId = res.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
            final String spec = resId > 0 ? res.getString(resId) : null;
            isNotchScreen = spec != null && !TextUtils.isEmpty(spec);
        } catch (Exception e) {

        }
        return isNotchScreen;
    }


    @Override
    public Rect getSafeRect(Activity activity) {
        if (hasNotch(activity)) {
            return new Rect(0, getStatusBarHeight(activity), 0, 0);
        }
        return new Rect(0, 0, 0, 0);
    }

    public static int getStatusBarHeight(Context context) {
        try {
            int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                return context.getResources().getDimensionPixelSize(resId);
            }
        } catch (Exception e) {


        }
        return 24;
    }
}
