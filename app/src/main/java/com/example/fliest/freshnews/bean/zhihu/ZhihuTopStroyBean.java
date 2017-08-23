package com.example.fliest.freshnews.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fliest on 2017/7/12.
 */


public class ZhihuTopStroyBean implements Serializable {

    @SerializedName("image")
    private String image;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        return "ZhihuTopStroyBean{" +
                "image='" + image + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
