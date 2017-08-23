package com.example.fliest.freshnews.api;

import com.example.fliest.freshnews.bean.zhihu.ZhihuDetailStroyBean;
import com.example.fliest.freshnews.bean.zhihu.ZhihuList;



import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Fliest on 2017/7/12.
 */

public interface ZhihuApi {

    @GET("http://news-at.zhihu.com/api/4/news/latest")
    Observable<ZhihuList> getLatestDialy();

    @GET("http://news-at.zhihu.com/api/4/news/before/{date}")
    Observable<ZhihuList> getLastDialy(@Path("date") String date);

    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<ZhihuDetailStroyBean> getDetailStroy(@Path("id") int id);
}
