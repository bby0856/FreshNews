package com.example.fliest.freshnews.task;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.fliest.freshnews.activity.MeiziDetailActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Fliest on 2017/7/25.
 */

public class SaveImageBitmapTask extends AsyncTask<Bitmap, Void, Boolean> {

    private Context mContext;
    private MeiziDetailActivity mActivity;
    private boolean downloadSuccess = true;

    public SaveImageBitmapTask(Context context) {
        mContext = context;
        mActivity = (MeiziDetailActivity) context;
    }

    @Override
    protected Boolean doInBackground(Bitmap... params) {
        Bitmap bitmap = params[0];
        try {
            saveImage(bitmap);
        } catch (Exception e) {
            e.printStackTrace();

            downloadSuccess = false;
        }

        return downloadSuccess;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        mActivity.showDownloadImageResult(aBoolean);
    }

    private void saveImage(Bitmap bitmap) throws IOException {

            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            File directory = new File(externalStorageDirectory, "freshnews");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File outFile = new File(directory, new Date().getTime() + ".jpg");
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(outFile);
            intent.setData(uri);
            mContext.sendBroadcast(intent);

    }
}
