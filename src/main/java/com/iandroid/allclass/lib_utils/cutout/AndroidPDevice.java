package com.iandroid.allclass.lib_utils.cutout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.WindowInsets;

@TargetApi(Build.VERSION_CODES.P)
public class AndroidPDevice implements ICutoutDevice {
    private static final String TAG = "AndroidPDevice";
    private Rect mSafeRect = null;

    public static boolean isApply() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    @Override
    public boolean hasNotch(Activity activity) {
        Rect safeRect = this.getSafeRect(activity);
        if (mSafeRect != null) {
            return mSafeRect.left != 0 || mSafeRect.top != 0 || mSafeRect.right != 0 || mSafeRect.bottom != 0;
        }
        return safeRect.left != 0 || safeRect.top != 0 || safeRect.right != 0 || safeRect.bottom != 0;
    }

    @Override
    public Rect getSafeRect(Activity activity) {
        WindowInsets insets = activity.getWindow().getDecorView().getRootWindowInsets();
        if (insets != null) {
            DisplayCutout displayCutout = insets.getDisplayCutout();
            if (displayCutout != null) {
                mSafeRect = new Rect(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetTop(), displayCutout.getSafeInsetRight(), displayCutout.getSafeInsetBottom());
                return mSafeRect;
            }
        }
        return new Rect(0, 0, 0, 0);
    }
}
