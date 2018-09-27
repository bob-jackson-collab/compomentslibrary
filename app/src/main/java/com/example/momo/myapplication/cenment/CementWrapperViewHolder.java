package com.example.momo.myapplication.cenment;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/21
 *   desc: MyApplication
 * </pre>
 */
//<editor-fold desc="Wrapper Model">
public abstract class CementWrapperViewHolder<VH extends CementViewHolder> extends CementViewHolder {
    @NonNull
    protected final VH childViewHolder;

    @NonNull
    public VH getChildViewHolder() {
        return childViewHolder;
    }

    public CementWrapperViewHolder(View itemView, @NonNull VH childViewHolder) {
        super(itemView);
        this.childViewHolder = childViewHolder;
    }

    @Override
    public boolean shouldSaveViewState() {
        return childViewHolder.shouldSaveViewState();
    }
}
