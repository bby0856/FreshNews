package com.example.fliest.freshnews.api;

import com.example.fliest.freshnews.Utils.Urls;
import com.example.fliest.freshnews.bean.topnews.TopnewsList;
import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Fliest on 2017/7/9.
 */

public interface TopNewsApi {

    @GET("http://c.m.163.com/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<TopnewsList> getNewsList(@Path("id") int id);

    @GET(Urls.DETAIL_URL+"{id}"+Urls.END_DETAIL_URL)
    Observable<JsonObject> getDetailNews(@Path("id") String id);
}
