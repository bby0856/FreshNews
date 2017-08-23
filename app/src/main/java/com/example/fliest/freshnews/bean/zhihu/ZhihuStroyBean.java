package com.example.fliest.freshnews.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Fliest on 2017/7/12.
 */


public class ZhihuStroyBean implements Serializable {

    @SerializedName("images")
    private String[] images;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ZhihuStroyBean{" +
                "images=" + Arrays.toString(images) +
                ", type=" + type +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
