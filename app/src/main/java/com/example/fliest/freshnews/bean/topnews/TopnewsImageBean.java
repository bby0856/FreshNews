package com.example.fliest.freshnews.bean.topnews;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fliest on 2017/7/21.
 */

public class TopnewsImageBean implements Serializable {
    @SerializedName("src")
    private String imageSrc;

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "TopnewsImageBean{" +
                "imageSrc='" + imageSrc + '\'' +
                '}';
    }
}
