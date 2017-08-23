package com.example.fliest.freshnews.presenter.gank;

import com.example.fliest.freshnews.api.ApiManager;
import com.example.fliest.freshnews.bean.gank.BeautyList;
import com.example.fliest.freshnews.fragment.MeiziFragment;
import com.example.fliest.freshnews.presenter.ManageSubscription;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fliest on 2017/7/13.
 */

public class GankPresenter extends ManageSubscription implements IGankPresenter {
    MeiziFragment mFragment;
    public GankPresenter(MeiziFragment meiziFragment){
        mFragment = meiziFragment;
    }
    @Override
    public void getMeiziList(int id) {
        Subscription subscription = ApiManager.getInstance().getGankApiService().getBeautyPic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeautyList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BeautyList list) {
                        mFragment.updateItems(list);
                    }
                });
        addSubscription(subscription);
    }
}
