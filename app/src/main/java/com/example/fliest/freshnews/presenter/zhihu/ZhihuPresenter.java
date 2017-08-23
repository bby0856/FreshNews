package com.example.fliest.freshnews.presenter.zhihu;

import com.example.fliest.freshnews.api.ApiManager;
import com.example.fliest.freshnews.bean.zhihu.ZhihuList;
import com.example.fliest.freshnews.fragment.ZhihuFragment;
import com.example.fliest.freshnews.presenter.ManageSubscription;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fliest on 2017/7/12.
 */

public class ZhihuPresenter extends ManageSubscription implements IZhihuPresenter {
    private ZhihuFragment fragment;

    public ZhihuPresenter(ZhihuFragment zhihuFragment){
        fragment = zhihuFragment;
    }

    @Override
    public void getLatestList() {
        Subscription subscription = ApiManager.getInstance().getZhihuService().getLatestDialy()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error:"+e.toString());
                    }

                    @Override
                    public void onNext(ZhihuList list) {
                        System.out.println("list:"+list.toString());
                        fragment.updateItems(list);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getLastList(String date) {
        Subscription subscription = ApiManager.getInstance().getZhihuService().getLastDialy(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error:"+e.toString());
                    }

                    @Override
                    public void onNext(ZhihuList list) {
                        System.out.println("list:"+list.toString());
                        fragment.updateItems(list);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getStoryDetail(int id) {

    }
}
