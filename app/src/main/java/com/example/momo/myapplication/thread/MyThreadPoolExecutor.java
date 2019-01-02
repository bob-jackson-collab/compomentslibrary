package com.example.momo.myapplication.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/12/07
 *   desc: MyApplication
 * </pre>
 */
public class MyThreadPoolExecutor extends ScheduledThreadPoolExecutor {


    public MyThreadPoolExecutor(int corePoolSize, int maxNumSize, long keepAliveTime, TimeUnit timeUnit,
                                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
        setMaximumPoolSize(maxNumSize);
        setKeepAliveTime(keepAliveTime, timeUnit);
    }
}
