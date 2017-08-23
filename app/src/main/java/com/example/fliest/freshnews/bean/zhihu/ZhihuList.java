package com.example.fliest.freshnews.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fliest on 2017/7/12.
 */

public class ZhihuList implements Serializable {

    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private ArrayList<ZhihuStroyBean> stroyList;
    @SerializedName("top_stories")
    private ArrayList<ZhihuTopStroyBean> topStroyList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhihuStroyBean> getStroyList() {
        return stroyList;
    }

    public void setStroyList(ArrayList<ZhihuStroyBean> stroyList) {
        this.stroyList = stroyList;
    }

    public ArrayList<ZhihuTopStroyBean> getTopStroyList() {
        return topStroyList;
    }

    public void setTopStroyList(ArrayList<ZhihuTopStroyBean> topStroyList) {
        this.topStroyList = topStroyList;
    }

    @Override
    public String toString() {
        return "ZhihuList{" +
                "date='" + date + '\'' +
                ", stroyList=" + stroyList +
                ", topStroyList=" + topStroyList +
                '}';
    }
}
