package com.example.fliest.freshnews.Utils;

import android.provider.Settings;

/**
 * Created by Fliest on 2017/7/10.
 */

public class TimeDelayUtil {

    public static void delay(int millionseconds){
        long beginningTime = System.currentTimeMillis();
        long currentTime  = 0;
        while ((currentTime - beginningTime)< millionseconds){
            currentTime = System.currentTimeMillis();
        }
    }
}
