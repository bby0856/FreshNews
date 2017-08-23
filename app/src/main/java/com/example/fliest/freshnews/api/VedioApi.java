package com.example.fliest.freshnews.api;

import com.example.fliest.freshnews.bean.vedio.VedioList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Fliest on 2017/8/5.
 */

public interface VedioApi {
    @GET("http://gank.io/api/data/休息视频/10/{id}")
    Observable<VedioList> getVedioList(@Path("id") int id);
}
