package com.example.fliest.freshnews.api;

import com.example.fliest.freshnews.bean.gank.BeautyList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Fliest on 2017/7/12.
 */

public interface GankApi {

    @GET("http://gank.io/api/data/福利/10/{id}")
    Observable<BeautyList> getBeautyPic(@Path("id") int id);
}
