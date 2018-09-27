package com.example.momo.myapplication;

import android.annotation.SuppressLint;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Case 基类
 *
 * @param <T>      返回数据
 * @param <Params> 请求参数
 */
public abstract class UseCase<T, Params> {

    @NonNull
    private final CompositeDisposable mDisposables;

    public UseCase() {
        this.mDisposables = new CompositeDisposable();
    }

    @NonNull
    protected abstract Flowable<T> buildObservable(Params params);

    @SuppressLint("CheckResult")
    public void execute(Params params, Consumer nextConsumer, Consumer errorConsumer) {
        Flowable<T> flowable = this.buildObservable(params);
        addDisposable(flowable.subscribe(nextConsumer, errorConsumer));
    }

    @SuppressLint("CheckResult")
    public <E extends Subscriber<? super T>> void execute(@NonNull E subcriber, @Nullable Params params, @Nullable Action onInterruptDispose) {
//        Preconditions.checkArgument(Disposable.class.isInstance(subcriber));
        Flowable<T> flowable = onInterruptDispose == null ? this.buildObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) : this.buildObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new InterruptDisposeTransformer<T>(onInterruptDispose));
        addDisposable((Disposable) flowable.subscribeWith(subcriber));
    }

    private void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    @CallSuper
    public void dispose() {
        if (!mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    @CallSuper
    public void clear() {
        mDisposables.clear();
    }
}
