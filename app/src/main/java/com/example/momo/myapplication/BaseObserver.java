package com.example.momo.myapplication;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * @param <T>
 */
public class BaseObserver<T> implements Observer {

    private Disposable mDisposable;
    @Override
    public void onSubscribe(Disposable d) {
       mDisposable = d;
    }

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public Disposable getDisposable() {
        return mDisposable;
    }
}
