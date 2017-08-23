package com.example.fliest.freshnews.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.fliest.freshnews.activity.MeiziDetailActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Fliest on 2017/7/25.
 */

public class SaveImageFileTask extends AsyncTask<String,Void,Boolean> {

    private Context mContext;
    private MeiziDetailActivity mActivity;
    private boolean downloadSuccess = true;

    public SaveImageFileTask(Context context){
        mContext = context;
        mActivity = (MeiziDetailActivity) context;
    }
    @Override
    protected Boolean doInBackground(String... params) {
        String url = params[0];
        try {

            FutureTarget<File> future = Glide.with(mContext)
                    .load(url)
                    .downloadOnly(100, 100);
            File cacheFile = future.get();

            saveImage(cacheFile);
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

    private void saveImage(File file) {
        try {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            File directory = new File(externalStorageDirectory,"freshnews");
            if(!directory.exists()){
                directory.mkdirs();
            }
            File outFile = new File(directory, new Date().getTime() + ".jpg");
            if(!outFile.exists()){
                outFile.createNewFile();
            }

            BufferedInputStream bufis  = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream bufos = new BufferedOutputStream(new FileOutputStream(outFile));

            int b = 0;
            while((b=bufis.read())!=-1){
                bufos.write(b);
            }

            bufis.close();;
            bufos.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(outFile);
            intent.setData(uri);
            mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
