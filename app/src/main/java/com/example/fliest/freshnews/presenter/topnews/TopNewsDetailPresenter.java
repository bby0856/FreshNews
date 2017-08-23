package com.example.fliest.freshnews.presenter.topnews;

import com.example.fliest.freshnews.Utils.NewsJsonUtil;
import com.example.fliest.freshnews.Utils.OkHttpUtils;
import com.example.fliest.freshnews.Utils.Urls;
import com.example.fliest.freshnews.activity.TopNewsDetailActivity;
import com.example.fliest.freshnews.api.ApiManager;
import com.example.fliest.freshnews.bean.topnews.TopnewsDetailBean;
import com.example.fliest.freshnews.presenter.ManageSubscription;
import com.google.gson.Gson;

import com.google.gson.JsonObject;


import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fliest on 2017/7/11.
 */

public class TopNewsDetailPresenter extends ManageSubscription implements ITopNewsPresenter {
    private TopNewsDetailActivity activity;
    private final Gson mGson;

    public TopNewsDetailPresenter(TopNewsDetailActivity topNewsDetailActivity) {
        activity = topNewsDetailActivity;
        mGson = new Gson();
    }

    @Override
    public void getNewsList(int id) {
    }

    @Override
    public void getNewsDetail(final String docid) {
        Subscription subscription = ApiManager.getInstance().getTopNewsService()
                .getDetailNews(docid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error:"+e.toString());
                        activity.showError(e.toString());
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        TopnewsDetailBean bean = NewsJsonUtil.readJsonToBean(jsonObject,docid);

                        activity.updateItems(bean);
                    }
                });
        addSubscription(subscription);
    }




    public void other(final String docid){
        String url = getDetailUrl(docid);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response:"+response);
                //activity.updateItems(response);
                //NewsDetailBean newsDetailBean = NewsJsonUtil.readJsonToBean(response,docid);
                //activity.updateItems(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                activity.showError(e.toString());
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.DETAIL_URL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }

}
