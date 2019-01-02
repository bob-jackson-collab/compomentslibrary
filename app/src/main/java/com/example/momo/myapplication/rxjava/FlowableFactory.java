package com.example.momo.myapplication.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/12/13
 *   desc: momodev
 * </pre>
 */
public class FlowableFactory {


//    public Flowable create(int type){
//
//    }

    public static void main(String[] args) {

        final List<String> o = new ArrayList<>();
        Subscriber<List<String>> subscriber = Flowable.merge(getFlowable1(), getFlowable2())
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Subscriber<List<String>>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        o.addAll(strings);
                        System.out.println(o.toString());

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private static Flowable<List<String>> getFlowable1() {
        return Flowable.fromCallable(new Callable<List<String>>() {

            @Override
            public List<String> call() {

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
                List<String> list = new ArrayList<>();
                list.add("11111");
                list.add("22222");
                list.add("33333");
                return list;
            }
        });
    }


    private static Flowable<List<String>> getFlowable2() {
        return Flowable.fromCallable(new Callable<List<String>>() {

            @Override
            public List<String> call() {

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                List<String> list = new ArrayList<>();
                list.add("44444");
                list.add("55555");
                list.add("6666");
                return list;
            }
        });
    }

}
