package com.example.fliest.freshnews.bean.vedio;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fliest on 2017/8/5.
 */

public class VedioList implements Serializable {

    @SerializedName("results")
    public ArrayList<VedioBean> vedioList;

    public ArrayList<VedioBean> getVedioList() {
        return vedioList;
    }

    public void setVedioList(ArrayList<VedioBean> vedioList) {
        this.vedioList = vedioList;
    }

    @Override
    public String toString() {
        return "VedioList{" +
                "vedioList=" + vedioList +
                '}';
    }
}
