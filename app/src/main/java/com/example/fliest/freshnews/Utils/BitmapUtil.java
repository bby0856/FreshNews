package com.example.fliest.freshnews.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by Fliest on 2017/7/25.
 */

public class BitmapUtil {

    public static Bitmap decodeSampleFromFile(File file, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(file.getAbsolutePath(),options);

        options.inSampleSize = calulateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(file.getAbsolutePath(),options);
    }

    public static int calulateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if(width > reqWidth || height >reqHeight){
            int halfWidth = width /2;
            int halfHeight = height /2;
            while((halfWidth/inSampleSize)>reqWidth || (halfHeight/inSampleSize)>reqHeight){
                inSampleSize *=2;
            }
        }

        return inSampleSize;
    }
}
