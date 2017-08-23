package com.example.fliest.freshnews.presenter.topnews;

import com.example.fliest.freshnews.api.ApiManager;
import com.example.fliest.freshnews.bean.topnews.TopnewsList;
import com.example.fliest.freshnews.fragment.TopNewsFragment;
import com.example.fliest.freshnews.presenter.ManageSubscription;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fliest on 2017/7/10.
 */

public class TopNewsPresenter extends ManageSubscription implements ITopNewsPresenter {
    TopNewsFragment fragment;
    public TopNewsPresenter(TopNewsFragment topNewsFragment){
        fragment = topNewsFragment;
    }

    @Override
    public void getNewsList(int id) {

        Subscription subscription = ApiManager.getInstance().getTopNewsService().getNewsList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopnewsList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        String error = e.toString();

                        fragment.showError(error);
                    }

                    @Override
                    public void onNext(TopnewsList list) {
                        System.out.println("list:"+list);

                        fragment.updateItems(list);
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void getNewsDetail(String docid) {
    }

}
