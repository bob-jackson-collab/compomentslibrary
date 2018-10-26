package com.example.momo.myapplication.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/10/16
 *   desc: MyApplication
 * </pre>
 */
public class Observable {

    private List<Observer> mObserverList = new ArrayList<>();


    private void registerObserver(Observer observer) {
        mObserverList.add(observer);
    }

    private void unregisterObserver(Observer observer) {
        mObserverList.remove(observer);
    }

    private void notifyData() {
        for (Observer observer : mObserverList) {
            if (observer != null) {
                observer.dataChange("1111");
            }
        }
    }

}
