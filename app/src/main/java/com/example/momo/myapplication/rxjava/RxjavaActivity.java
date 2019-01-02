package com.example.momo.myapplication.rxjava;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.momo.myapplication.R;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/12/13
 *   desc: MyApplication
 * </pre>
 */
public class RxjavaActivity extends AppCompatActivity {

    List<String> o = new ArrayList<>();
    final List<Flowable<List<String>>> flowables = new ArrayList<>();
    final Observable[] mObservables = new Observable[2];


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        flowables.add(getFlowable1());
        flowables.add(getFlowable2());


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
merge();
//                Flowable.fromCallable(new Callable<List<String>>() {
//                    @Override
//                    public List<String> call() throws Exception {
//                        try {
//                            Thread.sleep(3000);
//                        } catch (Exception e) {
//
//                        }
//                        List<String> list = new ArrayList<>();
//                        list.add("11111");
//                        list.add("22222");
//                        list.add("33333");
//                        return list;
//                    }
//                }).doOnNext(new Consumer<List<String>>() {
//                    @Override
//                    public void accept(List<String> strings) throws Exception {
//                        System.out.println("nima ");
//                    }
//                }).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSubscriber<List<String>>() {
//                            @Override
//                            public void onNext(List<String> strings) {
//                                System.out.println(strings.toString());
//                            }
//
//                            @Override
//                            public void onError(Throwable t) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
//                merge();
//                Flowable.mergeDelayError(flowables).subscribeOn(Schedulers.io())
//                        .doOnNext(new Consumer<List<String>>() {
//                            @Override
//                            public void accept(List<String> strings) throws Exception {
//                                Thread.sleep(6000);
//                                System.out.println("ysssssssssss");
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread(),true)
//                        .subscribeWith(new DisposableSubscriber<List<String>>() {
//                            @Override
//                            public void onNext(List<String> strings) {
//                                o.addAll(strings);
//
//                                System.out.println(o.toString());
//                            }
//
//                            @Override
//                            public void onError(Throwable t) {
//                                System.out.println(t.getMessage());
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });

            }
        });


    }

    private void merge() {
        DisposableSubscriber<List<String>> disposableSubscriber = Flowable.mergeDelayError(getFlowable1(), getFlowable2(),getFlowable3())
                .map(new Function<List<String>, List<String>>() {
                    @Override
                    public List<String> apply(List<String> strings) throws Exception {
                        System.out.println("usersx");
                        return strings;
                    }
                })
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        System.out.println("uuuusssss");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeWith(new DisposableSubscriber<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {
                        o.addAll(strings);

                        System.out.println(o.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
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
//                throw new IllegalArgumentException("canshucuowu");
                return list;
            }
        });
    }

    private static Flowable<List<String>> getFlowable3() {
        return Flowable.fromCallable(new Callable<List<String>>() {

            @Override
            public List<String> call() {

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                List<String> list = new ArrayList<>();
                list.add("77777");
                list.add("88888");
                list.add("99999");
//                throw new IllegalArgumentException("canshucuowu");
                return list;
            }
        });
    }


    private static Flowable<List<String>> getFlowable2() {
        return Flowable.fromCallable(new Callable<List<String>>() {

            @Override
            public List<String> call() {

                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                }
                List<String> list = new ArrayList<>();
                list.add("44444");
                list.add("55555");
                list.add("66666");
                return list;
            }
        });
    }
}
