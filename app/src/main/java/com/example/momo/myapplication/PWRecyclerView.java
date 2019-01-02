package com.example.momo.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/12/29
 *   desc: MyApplication
 * </pre>
 */
public class PWRecyclerView extends RecyclerView {

    private boolean isScrolling = false;

    public PWRecyclerView(@NonNull Context context) {
        super(context);
    }

    public PWRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PWRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public class AutoLoadScrollListener extends OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case SCROLL_STATE_IDLE:
                    isScrolling = false;
                    break;
                case SCROLL_STATE_DRAGGING:
                    isScrolling = false;
                    break;
                case SCROLL_STATE_SETTLING:
                    isScrolling = true;
                    break;
                default:
                    break;

            }
        }
    }
}
