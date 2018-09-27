package com.example.momo.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class LazyFragment extends Fragment {

    private static final String TAG = "LazyFragment";
    private boolean isActivityCreated = false;
    private boolean isViewCreated = false;
    private boolean isPrepared = false;
    private boolean isLazyLoadFinished = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isActivityCreated = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        initView();
        isViewCreated = true;
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    protected boolean canDoLazyLoad() {
        return isPrepared && !isLazyLoadFinished && getUserVisibleHint() && isViewCreated;
    }

    protected void doLazyLoad() {
    }

    private void initView() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        isActivityCreated = true;
        super.onActivityCreated(savedInstanceState);
        if (canDoLazyLoad()) {
            doLazyLoad();
            setLoadFinished();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        }
    }

    private void onVisible() {
        if (canDoLazyLoad()) {
            doLazyLoad();
            setLoadFinished();
        }
    }

    protected void setLoadFinished() {
        isLazyLoadFinished = true;
    }

    public boolean isCreated() {
        return isActivityCreated;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isActivityCreated = false;
    }
}
