package com.example.fliest.freshnews.presenter;

import rx.Subscription;
import rx.internal.subscriptions.CancellableSubscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fliest on 2017/7/10.
 */

public class ManageSubscription {
    CompositeSubscription mSubscription;
    public void addSubscription(Subscription s){
        if(mSubscription == null){
            mSubscription = new CompositeSubscription();
        }

        mSubscription.add(s);
    }

    public void unSubscription(){
        if(mSubscription!= null){
            mSubscription.unsubscribe();
        }
    }
}
