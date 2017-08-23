package com.example.fliest.freshnews.bean.gank;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fliest on 2017/7/12.
 */

public class BeautyList implements Serializable {
    @SerializedName("results")
    public ArrayList<BeautyBean> results;

    public ArrayList<BeautyBean> getResults() {
        return results;
    }

    public void setResults(ArrayList<BeautyBean> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BeautyBean{" +
                "results=" + results +
                '}';
    }
}
