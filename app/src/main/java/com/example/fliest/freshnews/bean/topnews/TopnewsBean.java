package com.example.fliest.freshnews.bean.topnews;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fliest on 2017/7/12.
 */

public class TopnewsBean implements Serializable {

    @SerializedName("docid")
    private String docid;
    @SerializedName("title")
    private String title;
    @SerializedName("source")
    private String source;
    @SerializedName("imgsrc")
    private String imgsrc;
    @SerializedName("ptime")
    private String time;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TopnewsBean{" +
                "docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
