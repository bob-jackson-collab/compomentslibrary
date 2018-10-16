package com.example.momo.myapplication.cenment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
public class CementViewHolder extends RecyclerView.ViewHolder {

    @Nullable
    private CementModel mCementModel;

    public CementViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    void bind(@NonNull CementModel model, @Nullable List<Object> payloads) {
        if (payloads != null && !payloads.isEmpty()) {
            // noinspection unchecked
            model.bindData(this, payloads);
        } else {
            // noinspection unchecked
            model.bindData(this);
        }

        this.mCementModel = model;
    }

    void unbind() {
        if (mCementModel == null) return;
        // noinspection unchecked
        mCementModel.unbind(this);
        mCementModel = null;
    }

    boolean shouldSaveViewState() {
        return mCementModel != null && mCementModel.shouldSaveViewState();
    }

    @Nullable
    public CementModel getCementModel() {
        return mCementModel;
    }
}