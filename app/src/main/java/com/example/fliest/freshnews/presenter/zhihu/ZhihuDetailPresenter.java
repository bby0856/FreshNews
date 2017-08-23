package com.example.fliest.freshnews.presenter.zhihu;

import com.example.fliest.freshnews.activity.ZhihuiDetailActivity;
import com.example.fliest.freshnews.api.ApiManager;
import com.example.fliest.freshnews.bean.zhihu.ZhihuDetailStroyBean;
import com.example.fliest.freshnews.presenter.ManageSubscription;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fliest on 2017/7/12.
 */

public class ZhihuDetailPresenter extends ManageSubscription implements IZhihuPresenter {
    private ZhihuiDetailActivity mActivity;

    public ZhihuDetailPresenter(ZhihuiDetailActivity zhihuiDetailActivity){
        mActivity = zhihuiDetailActivity;
    }

    @Override
    public void getLatestList() {

    }

    @Override
    public void getLastList(String date) {

    }

    @Override
    public void getStoryDetail(int id) {
        Subscription subscription = ApiManager.getInstance().getZhihuService().getDetailStroy(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDetailStroyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showError(e.toString());
                    }

                    @Override
                    public void onNext(ZhihuDetailStroyBean bean) {
                        mActivity.updateItems(bean);
                    }
                });
        addSubscription(subscription);
    }
}
