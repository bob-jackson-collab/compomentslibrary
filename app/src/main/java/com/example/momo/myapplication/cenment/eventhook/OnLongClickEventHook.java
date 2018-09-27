package com.example.momo.myapplication.cenment.eventhook;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.momo.myapplication.cenment.CementAdapter;
import com.example.momo.myapplication.cenment.CementModel;
import com.example.momo.myapplication.cenment.CementViewHolder;

import static android.support.v7.widget.RecyclerView.NO_POSITION;


/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/25
 *   desc: MyApplication
 * </pre>
 */
public abstract class OnLongClickEventHook<VH extends CementViewHolder> extends EventHook<VH> {

    public abstract boolean onLongClick(@NonNull View view, @NonNull VH viewHolder, @NonNull CementModel cementModel, int position);

    public OnLongClickEventHook(@NonNull Class<VH> clazz) {
        super(clazz);
    }

    @Override
    public void onEvent(@NonNull View view, @NonNull final VH viewHolder, @NonNull CementAdapter adapter) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                CementModel cementModel = viewHolder.getCementModel();
                return position != NO_POSITION && cementModel != null && OnLongClickEventHook.this.onLongClick(v, viewHolder, cementModel, position);
            }
        });
    }
}
