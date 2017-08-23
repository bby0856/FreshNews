package com.example.fliest.freshnews;

import android.app.Application;
import android.content.Context;

/**
 * Created by xinghongfei on 16/8/12.
 */
public class MyApplication extends Application {

    public final static String TAG = "BaseApplication";
    public final static boolean DEBUG = true;
    private static MyApplication myApplication;
    private static int mainTid;



    public static Application getContext() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;


    }

    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return myApplication;
    }




}
