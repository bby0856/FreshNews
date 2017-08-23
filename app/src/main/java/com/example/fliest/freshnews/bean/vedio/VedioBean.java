package com.example.fliest.freshnews.bean.vedio;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fliest on 2017/8/5.
 */

public class VedioBean implements Serializable {

    @SerializedName("_id")
    public String id;
    @SerializedName("desc")
    public String desc;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VedioBean{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
