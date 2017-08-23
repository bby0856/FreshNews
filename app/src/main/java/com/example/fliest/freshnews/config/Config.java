package com.example.fliest.freshnews.config;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fliest on 2017/7/7.
 */

public class Config {

    private static String[] NAVIGATIONITEM = new String[]{"首页", "知乎", "福利", "视频"};

    public static String[] getChannel(Context context) {
        File mFile = new File(context.getExternalCacheDir() + "mychannelcache.txt");
        ArrayList<String> myChannelList = new ArrayList<String>();

        if (mFile.exists()) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(mFile));
                myChannelList = (ArrayList<String>) in.readObject();
                String[] mcStr = new String[myChannelList.size()];
                NAVIGATIONITEM = myChannelList.toArray(mcStr);

                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return NAVIGATIONITEM;
    }


    private final static String[] VEDIOURL = new String[]{
            "http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4",
            "http://video.jiecao.fm/8/16/%E9%B8%AD%E5%AD%90.mp4",
            "http://video.jiecao.fm/8/16/%E9%A9%BC%E8%83%8C.mp4",
            "http://video.jiecao.fm/5/1/%E8%87%AA%E5%8F%96%E5%85%B6%E8%BE%B1.mp4",
            "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4",
            "http://video.jiecao.fm/11/23/xu/%E5%A6%B9%E5%A6%B9.mp4",
            "http://video.jiecao.fm/8/17/bGQS3BQQWUYrlzP1K4Tg4Q__.mp4",
            "http://video.jiecao.fm/11/24/xin/-%2024%20-%20.mp4",
            "http://video.jiecao.fm/11/24/6/%E9%85%92%E9%A9%BE.mp4",
            "http://video.jiecao.fm/11/23/6/%E7%8B%97.mp4",
            "http://video.jiecao.fm/11/23/6/%E5%AD%A9%E5%AD%90.mp4",
            "http://video.jiecao.fm/11/24/xu/%E6%97%A5%E5%8E%86.mp4",
            "http://video.jiecao.fm/11/26/-iDareX.mp4",
            "http://video.jiecao.fm/11/24/6/%E5%AD%94%E6%98%8E%E7%81%AF.mp4"};

    public static String getVedioUrlRandom(){
        Random random = new Random();
        int id = random.nextInt(VEDIOURL.length);

        return VEDIOURL[id];
    }
}
