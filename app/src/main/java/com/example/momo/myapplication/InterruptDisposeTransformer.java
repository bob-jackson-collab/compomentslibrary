package com.example.momo.myapplication;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class InterruptDisposeTransformer<T> implements FlowableTransformer<T, T> {
    private boolean isErrorOrComplete = false;
    @NonNull
    private final Action doOnInterruptDispose;

    public InterruptDisposeTransformer(@NonNull Action doOnInterruptDispose) {
        this.doOnInterruptDispose = doOnInterruptDispose;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                isErrorOrComplete = true;
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                isErrorOrComplete = true;
            }
        }).doOnCancel(new Action() {
            @Override
            public void run() throws Exception {
                if (!isErrorOrComplete) {
                    doOnInterruptDispose.run();
                }
            }
        });
    }
}
