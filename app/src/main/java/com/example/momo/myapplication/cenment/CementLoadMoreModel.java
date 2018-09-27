package com.example.momo.myapplication.cenment;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.example.momo.myapplication.cenment.CementAdapter;
import com.example.momo.myapplication.cenment.CementModel;
import com.example.momo.myapplication.cenment.CementViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/25
 *   desc: MyApplication
 * </pre>
 */
public abstract class CementLoadMoreModel<VH extends CementViewHolder> extends CementModel<VH> {

    public static final int START = 0;
    public static final int COMPLETE = 1;
    public static final int FAILED = 2;

    @IntDef({START, COMPLETE, FAILED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreState {
    }

    @LoadMoreState
    private int state = COMPLETE;

    public final void setState(@LoadMoreState int state) {
        this.state = state;
    }

    @Override
    public void bindData(@NonNull VH holder) {
        switch (state) {
            case START:
                onLoadMoreStart(holder);
                break;
            case COMPLETE:
                onLoadMoreComplete(holder);
                break;
            case FAILED:
                onLoadMoreFailed(holder);
                break;
            default:
                break;
        }
    }

    /**
     * before loading, show "loading..."
     */
    public abstract void onLoadMoreStart(@NonNull VH holder);

    /**
     * after loading and showing data, show "click to load" for next loading
     */
    public abstract void onLoadMoreComplete(@NonNull VH holder);

    /**
     * after loading failed, show "click to retry"
     */
    public abstract void onLoadMoreFailed(@NonNull VH holder);
}
