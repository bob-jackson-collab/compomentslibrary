package com.example.momo.myapplication.cenment.eventhook;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.momo.myapplication.cenment.CementAdapter;
import com.example.momo.myapplication.cenment.CementModel;
import com.example.momo.myapplication.cenment.CementViewHolder;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/19
 *   desc: MyApplication
 * </pre>
 */
public abstract class OnClickEventHook<VH extends CementViewHolder> extends EventHook<VH> {

    public OnClickEventHook(@NonNull Class<VH> clazz) {
        super(clazz);
    }

    @Override
    public void onEvent(@NonNull final View view, @NonNull final VH viewHolder, @NonNull final CementAdapter adapter) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                //            CementModel cementModel = adapter.ge
                OnClickEventHook.this.onClick(view, viewHolder, position, null);
            }
        });
    }

    public abstract void onClick(@NonNull View view, @NonNull VH viewHolder, int position, @NonNull CementModel cementModel);
}
