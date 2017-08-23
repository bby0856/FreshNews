package com.example.fliest.freshnews.bean.topnews;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fliest on 2017/7/12.
 */

public class TopnewsList implements Serializable {
    @SerializedName("T1348647909107")
    private ArrayList<TopnewsBean> topnewsList;

    public ArrayList<TopnewsBean> getTopnewsList() {
        return topnewsList;
    }

    public void setTopnewsList(ArrayList<TopnewsBean> topnewsList) {
        this.topnewsList = topnewsList;
    }

    @Override
    public String toString() {
        return "TopnewsList{" +
                "topnewsList=" + topnewsList +
                '}';
    }
}
