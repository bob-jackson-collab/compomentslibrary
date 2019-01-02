package com.example.momo.myapplication.thread;

import android.app.Application;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/12/07
 *   desc: MyApplication
 * </pre>
 */
public class ThreadUtils {

    public static final String TAG = "ThreadUtils";

    /**
     * 处理耗时且优先级较低的任务，单个线程逐次来处理
     */
    @Nullable
    private ThreadPoolExecutorInfo innerExecutorInfo;
    /**
     * 处理本地耗时任务，如IO操作、一些计算等
     */
    @Nullable
    private ThreadPoolExecutorInfo localExecutorInfo;

    /**
     * 一般用于处理及时网络请求、及时任务等，优先级较高
     */
    @Nullable
    private ThreadPoolExecutorInfo rightNowExecutorInfo;

    private static final int INNER_THREAD_SIZE = 2;
    private static final int RIGHT_NOW_THREAD_SIZE = 10;
    private static final int RIGHT_NOW_LOCAL_THREAD_SIZE = 3;

    private static final TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;
    private static final long DEFAULT_KEEP_ALIVE_TIME = 60L;

    /**
     * 对执行时间要求不高
     */
    public static final int TYPE_INNER = 1;
    /**
     * 立即执行
     */
    public static final int TYPE_RIGHT_NOW = 2;
    /**
     * 立即执行，本地操作：文件、DB查询等
     */
    public static final int TYPE_RIGHT_LOCAL_NOW = 3;


    @IntDef({TYPE_RIGHT_LOCAL_NOW, TYPE_RIGHT_NOW, TYPE_INNER})
    @Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ThreadType {

    }

    @NonNull
    private synchronized ThreadPoolExecutorInfo getExecutorInfo(@ThreadType int type) {
        switch (type) {
            case TYPE_INNER:
                if (null == innerExecutorInfo) {
                    innerExecutorInfo = new ThreadPoolExecutorInfo(type, INNER_THREAD_SIZE, INNER_THREAD_SIZE,
                            DEFAULT_KEEP_ALIVE_TIME, DEFAULT_UNIT);
                }
                return innerExecutorInfo;
            case TYPE_RIGHT_NOW:
                if (null == rightNowExecutorInfo) {
                    rightNowExecutorInfo = new ThreadPoolExecutorInfo(type, RIGHT_NOW_THREAD_SIZE, RIGHT_NOW_THREAD_SIZE,
                            DEFAULT_KEEP_ALIVE_TIME * 2, DEFAULT_UNIT);
                }
                return rightNowExecutorInfo;
            case TYPE_RIGHT_LOCAL_NOW:
                if (null == localExecutorInfo) {
                    localExecutorInfo = new ThreadPoolExecutorInfo(type, RIGHT_NOW_LOCAL_THREAD_SIZE, RIGHT_NOW_LOCAL_THREAD_SIZE,
                            DEFAULT_KEEP_ALIVE_TIME, DEFAULT_UNIT);
                }
                return localExecutorInfo;
            default: {
                throw new IllegalArgumentException("type=" + type + " not recognized");
            }
        }
    }

    @NonNull
    private static <V> Callable<V> withDebug(@NonNull final Callable<V> callable) {
        return BuildConfig.DEBUG ? new Callable<V>() {
            @Override
            public V call() throws Exception {
                Log.e(TAG, "debug");
                return callable.call();
            }
        } : callable;
    }


    @NonNull
    private ScheduledThreadPoolExecutor getExecutor(@ThreadType int type) {
        return getExecutorInfo(type).get();
    }

    private void execute(@ThreadType int type, @Nullable Runnable command) {
        schedule(type, command, 0, TimeUnit.NANOSECONDS);
    }

    public ScheduledFuture<?> schedule(@ThreadType int type, @Nullable Runnable command, long delay, @Nullable TimeUnit unit) {
        if (command == null) {
            throw new IllegalArgumentException("command is null");
        }

        return getExecutor(type).schedule(command, delay, unit);
    }

    public void shutDown(@ThreadType int type) {
        getExecutor(type).shutdown();
    }

    public synchronized void shutDownAll() {
        if (innerExecutorInfo != null) {
            innerExecutorInfo.shutDown();
        }
        if (localExecutorInfo != null) {
            localExecutorInfo.shutDown();
        }
        if (rightNowExecutorInfo != null) {
            rightNowExecutorInfo.shutDown();
        }
    }


    private final class ThreadPoolExecutorInfo {

        @Nullable
        private MyThreadPoolExecutor myThreadPoolExecutor;
        @ThreadType
        private int type;
        private int coreSize;
        private int maxNumSize;
        private long keepLiveTime;
        @NonNull
        private TimeUnit mTimeUnit;

        public ThreadPoolExecutorInfo(int type, int coreSize, int maxNumSize, long keepLiveTime, @NonNull TimeUnit timeUnit) {
            this.type = type;
            this.coreSize = coreSize;
            this.maxNumSize = maxNumSize;
            this.keepLiveTime = keepLiveTime;
            this.mTimeUnit = timeUnit;
        }

        private MyThreadPoolExecutor get() {
            if (myThreadPoolExecutor == null) {
                myThreadPoolExecutor = new MyThreadPoolExecutor(coreSize, maxNumSize, keepLiveTime, mTimeUnit,
                        new LinkedBlockingQueue<Runnable>(), new MyThreadFactory(type), new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                });
                myThreadPoolExecutor.allowCoreThreadTimeOut(true);
            }
            return myThreadPoolExecutor;
        }

        private synchronized void shutDown() {
            if (myThreadPoolExecutor != null) {
                try {
                    myThreadPoolExecutor.shutdownNow();
                } catch (Exception e) {

                } finally {
                    myThreadPoolExecutor = null;
                }
            }
        }
    }


    public class MyThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(1);

        @ThreadType
        private int type;

        public MyThreadFactory(@ThreadType int type) {
            this.type = type;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            String threadName = "My" + type + "#Thread" + count.incrementAndGet();
            MyThread myThread = new MyThread(r, threadName);
            if (type == TYPE_RIGHT_NOW) {
                myThread.setPriority(Thread.MAX_PRIORITY);
            }
            return myThread;
        }
    }

    class MyThread extends Thread {

        public MyThread(Runnable target, String name) {
            super(target, name);
        }
    }

}
