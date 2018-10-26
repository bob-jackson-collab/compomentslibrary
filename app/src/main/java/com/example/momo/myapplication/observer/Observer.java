package com.example.momo.myapplication.observer;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/10/16
 *   desc: MyApplication
 * </pre>
 */
public class Observer {

    private String data;

    public Observer(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void dataChange(String data) {
        System.out.println(data);
    }

}
