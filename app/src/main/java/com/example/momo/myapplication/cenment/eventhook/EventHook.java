package com.example.momo.myapplication.cenment.eventhook;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.momo.myapplication.cenment.CementAdapter;
import com.example.momo.myapplication.cenment.CementViewHolder;

import java.util.List;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
public abstract class EventHook<VH extends CementViewHolder> {

    @NonNull
    protected final Class<VH> mClazz;


    public EventHook(@NonNull Class<VH> clazz) {
        this.mClazz = clazz;
    }

    public abstract void onEvent(@NonNull View view, @NonNull VH viewHolder, @NonNull CementAdapter adapter);

    @Nullable
    public View onBind(@NonNull VH viewHolder) {
        return null;
    }

    @Nullable
    public List<? extends View> onBindMany(@NonNull VH viewHolder) {
        return null;
    }
}
