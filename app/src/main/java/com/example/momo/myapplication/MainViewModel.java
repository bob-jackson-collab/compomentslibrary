package com.example.momo.myapplication;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 *
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<String> mLiveData;
    private MainRepository mMainRepository;

    public void init() {
        mLiveData = new MutableLiveData<>();
        mMainRepository = new MainRepository();
    }

    public void getNetData() {
        mLiveData.setValue(mMainRepository.getData());
    }

    public MutableLiveData<String> getLiveData() {
        return mLiveData;
    }
}
