package com.example.fliest.freshnews.bean.topnews;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fliest on 2017/7/12.
 */

public class TopnewsDetailBean implements Serializable {

    @SerializedName("body")
    private String body;
    @SerializedName("docid")
    private String docid;
    @SerializedName("title")
    private String title;
    @SerializedName("source")
    private String source;
    @SerializedName("ptime")
    private String time;
    @SerializedName("img")
    private ArrayList<TopnewsImageBean> img;
    @SerializedName("shareLink")
    private String shareLink;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<TopnewsImageBean> getImg() {
        return img;
    }

    public void setImg(ArrayList<TopnewsImageBean> img) {
        this.img = img;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    @Override
    public String toString() {
        return "TopnewsDetailBean{" +
                "body='" + body + '\'' +
                ", docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", time='" + time + '\'' +
                ", img=" + img +
                ", shareLink='" + shareLink + '\'' +
                '}';
    }
}
