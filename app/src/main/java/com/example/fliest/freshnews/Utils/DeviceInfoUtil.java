package com.example.fliest.freshnews.Utils;

import android.content.Context;
import android.view.WindowManager;

import static java.security.AccessController.getContext;

/**
 * Created by Fliest on 2017/7/12.
 */

public class DeviceInfoUtil {

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();

        return width;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int height = wm.getDefaultDisplay().getHeight();

        return height;
    }
}
