package com.example.fliest.freshnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fliest.freshnews.MainActivity;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.widget.FlowLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ChannelActivity extends AppCompatActivity {
    FlowLayout idFlowlayout1;
    FlowLayout idFlowlayout2;
    LayoutInflater mInflater;


    private String[] oriRecommendableChannel = new String[]{"首页", "知乎", "福利", "视频", "推荐", "热点", "社会", "娱乐", "科技", "汽车", "体育", "财经", "军事", "国际"};
    private String[] ori = new String[]{"首页", "知乎", "福利", "视频", "推荐", "热点", "社会", "娱乐", "科技", "汽车", "体育", "财经", "军事", "国际"};
    private String[] oriChannel = new String[30];
    private String lockStr = "首页知乎福利视频";
    private String[] recommendableChannel = new String[14];
    private File mFile;
    private File rFile;
    private String[] myChannel = new String[14];

    //new String[]{ "时尚", "游戏", "旅游", "历史", "探索", "美食", "育儿", "养生", "故事", "美文"};

    private ArrayList<TextView> textViewList = new ArrayList<TextView>();
    private ArrayList<String> myChannelList = new ArrayList<String>();
    private ArrayList<String> recommendableChannelList = new ArrayList<String>();
    private String[] mRcStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_channel);
        mFile = new File(getExternalCacheDir() + "mychannelcache.txt");
        rFile = new File(getExternalCacheDir() + "recchannelcache.txt");


        if (rFile.exists()) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(rFile));
                recommendableChannelList = (ArrayList<String>) in.readObject();
                mRcStr = new String[recommendableChannelList.size()];
                mRcStr = recommendableChannelList.toArray(mRcStr);
                recommendableChannel = convertStr(mRcStr,recommendableChannel);

                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            recommendableChannel = oriRecommendableChannel;
        }

        if (mFile.exists()) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(mFile));
                myChannelList = (ArrayList<String>) in.readObject();
                String[] mcStr = new String[myChannelList.size()];
                mcStr =  myChannelList.toArray(mcStr);
                myChannel = convertStr(mcStr,myChannel);

                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            myChannel = oriChannel;
        }


        idFlowlayout1 = (FlowLayout) findViewById(R.id.id_flowlayout1);
        idFlowlayout2 = (FlowLayout) findViewById(R.id.id_flowlayout2);
        ImageView imageView = (ImageView) findViewById(R.id.image_channel_close);


        mInflater = LayoutInflater.from(this);

        init();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ChannelActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private String[] getStringFromStrs(int[] ids){
        String[] strs = new String[ids.length];
        for(int i=0;i<ids.length;i++){
            strs[i] = ori[i];
        }

        return strs;
    }



    private int[] getIdFromLayout(FlowLayout layout){
        int count = layout.getChildCount();
        int[] ids = new int[count];
        for(int i=0;i<count;i++){
            View child = layout.getChildAt(i);
            int id = (int) child.getTag();
            ids[i] = id;
        }
       return ids;
    }

    private void init() {
        for (int i = 0; i < myChannelList.size(); i++) {
            final TextView tv1 = (TextView) mInflater.inflate(R.layout.layout_item_channel, idFlowlayout1, false);

            tv1.setTag(i);
            tv1.setText(ori[i]);
            idFlowlayout1.addView(tv1);
            if(!lockStr.contains(ori[i])){
                tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) v.getTag() ;
                        idFlowlayout1.removeView(v);
                        initFlowlayout2(i);
                    }
                });
            }

        }

        for (int i = 0; i < 14 - myChannelList.size(); i++) {
            final TextView tv2 = (TextView) mInflater.inflate(R.layout.layout_item_channel, idFlowlayout2, false);

            tv2.setTag(i+myChannelList.size());
            tv2.setText(ori[i+myChannelList.size()]);
            idFlowlayout2.addView(tv2);
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) v.getTag();

                    idFlowlayout2.removeView(v);
                    addViewToFlowlayout1(i);
                }
            });
        }
    }

    public void initFlowlayout2(int id) {
        TextView tv2 = (TextView) mInflater.inflate(R.layout.layout_item_channel, idFlowlayout2, false);

        String text = ori[id];

        tv2.setText(text);
        tv2.setTag(id);
        idFlowlayout2.addView(tv2);

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                idFlowlayout2.removeView(v);
                addViewToFlowlayout1(i);
            }
        });
    }

    public void addViewToFlowlayout1(int i) {

        TextView tv1 = (TextView) mInflater.inflate(R.layout.layout_item_channel, idFlowlayout1, false);
        String text = ori[i];
        tv1.setText(text);
        tv1.setTag(i);
        idFlowlayout1.addView(tv1);



        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                String str = ori[i];
                if(!lockStr.contains(str)){
                    idFlowlayout1.removeView(v);
                    initFlowlayout2(i);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        int[] ids1 = getIdFromLayout(idFlowlayout1);

        int[] ids2 = getIdFromLayout(idFlowlayout2);

        String[] str1 = getStringFromStrs(ids1);

        String[] str2 = getStringFromStrs(ids2);
        System.out.println("str1.length():"+str1.length);

        setListToFile(getArrayListFromString(str1),mFile);
        setListToFile(getArrayListFromString(str2),rFile);

        setResult(2);
    }

    public ArrayList<String> getArrayListFromString(String[] str) {
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < str.length; i++) {
            try {
                if (str[i] != null && !str[i].equals("-1")) {
                    list.add(str[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private void setListToFile(ArrayList<String> list, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(list);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String[] convertStr(String[] sor , String[] dst){
        for(int i=0;i<sor.length;i++){
            dst[i] = sor[i];
        }
        return dst;
    }


}
