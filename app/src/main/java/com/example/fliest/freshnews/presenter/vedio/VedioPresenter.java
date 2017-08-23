package com.example.fliest.freshnews.presenter.vedio;

import com.example.fliest.freshnews.api.ApiManager;
import com.example.fliest.freshnews.bean.vedio.VedioList;
import com.example.fliest.freshnews.fragment.VedioFragment;
import com.example.fliest.freshnews.presenter.ManageSubscription;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fliest on 2017/8/5.
 */

public class VedioPresenter extends ManageSubscription implements IVedioPresenter {
    private VedioFragment mFragment;
    public VedioPresenter(VedioFragment vedioFragment){
        mFragment = vedioFragment;
    }
    @Override
    public void getVedioList(int id) {
        Subscription subscription = ApiManager.getInstance().getVedioService().getVedioList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VedioList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VedioList lists) {
                        System.out.println("Vedio:"+lists.toString());
                        mFragment.updateItems(lists);
                    }
                });

        addSubscription(subscription);
    }
}
