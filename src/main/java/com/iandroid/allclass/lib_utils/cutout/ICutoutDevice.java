package com.iandroid.allclass.lib_utils.cutout;

import android.app.Activity;
import android.graphics.Rect;

public interface ICutoutDevice {
    boolean hasNotch(Activity activity);

    Rect getSafeRect(Activity context);
}
