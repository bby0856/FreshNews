package com.example.fliest.freshnews.api;

import com.example.fliest.freshnews.MyApplication;
import com.example.fliest.freshnews.Utils.NetworkUtil;
import com.example.fliest.freshnews.bean.vedio.VedioBean;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Fliest on 2017/7/9.
 */

public class ApiManager {
    private static ApiManager mApiManager = new ApiManager();
    private Object monitor = new Object();

    private TopNewsApi topNewsApi;
    private ZhihuApi zhihuApi;
    private GankApi gankApi;
    private VedioApi vedioApi;

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetworkUtil.isNetWorkAvailable(MyApplication.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    private static File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "zhihuCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
           // .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .cache(cache)
            .build();

    /*private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .build();*/

    private ApiManager() {
    }

    public static ApiManager getInstance() {
        return mApiManager;
    }

    public TopNewsApi getTopNewsService() {
        if (topNewsApi == null) {
            synchronized (monitor) {
                if (topNewsApi == null) {
                    topNewsApi = new Retrofit.Builder()
                            .baseUrl("http://c.m.163.com/")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(TopNewsApi.class);
                }
            }
        }

        return topNewsApi;
    }


    public ZhihuApi getZhihuService() {
        if (zhihuApi == null) {
            synchronized (monitor) {
                if (zhihuApi == null) {
                    zhihuApi = new Retrofit.Builder()
                            .baseUrl("http://news-at.zhihu.com/")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(ZhihuApi.class);
                }
            }
        }
        return zhihuApi;
    }

    public GankApi getGankApiService(){
        if(gankApi == null){
            synchronized (monitor){
                if(gankApi==null){
                    gankApi = new Retrofit.Builder()
                            .baseUrl("http://gank.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(GankApi.class);
                }
            }
        }
        return gankApi;
    }

    public VedioApi getVedioService() {
        if (vedioApi == null) {
            synchronized (monitor) {
                if (vedioApi == null) {
                    vedioApi = new Retrofit.Builder()
                            .baseUrl("http://gank.io/")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(VedioApi.class);
                }
            }
        }

        return vedioApi;
    }


}
