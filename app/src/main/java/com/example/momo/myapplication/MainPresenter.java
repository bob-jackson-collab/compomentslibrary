package com.example.momo.myapplication;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 *
 */
public class MainPresenter implements IMainPresenter {

    private static final String TAG = "MainPresenter";

    private IMainView mMainView;

    private void bindView(IMainView mainView) {
        this.mMainView = mainView;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        Log.e(TAG, "onCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        Log.e(TAG, "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        Log.e(TAG, "onPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
    }
}
