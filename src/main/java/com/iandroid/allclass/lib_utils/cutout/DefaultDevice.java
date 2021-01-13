package com.iandroid.allclass.lib_utils.cutout;

import android.app.Activity;
import android.graphics.Rect;


public class DefaultDevice implements ICutoutDevice {
    private static final String TAG = "DefaultDevice";

    @Override
    public boolean hasNotch(Activity activity) {
        return false;
    }


    @Override
    public Rect getSafeRect(Activity activity) {
        return new Rect(0, 0, 0, 0);
    }
}
