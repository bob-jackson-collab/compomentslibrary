package com.example.momo.myapplication.cenment;

import android.support.annotation.NonNull;

public interface IDiffUtilHelper<T> {
    /**
     * see {@link android.support.v7.util.DiffUtil.Callback#areItemsTheSame(int, int)}
     */
    boolean isItemTheSame(@NonNull T item);

    /**
     * see {@link android.support.v7.util.DiffUtil.Callback#areContentsTheSame(int, int)}
     */
    boolean isContentTheSame(@NonNull T item);
}