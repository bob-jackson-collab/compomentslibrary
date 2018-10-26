package com.example.momo.myapplication.cenment;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by xudong on 16/8/12.
 */

public class LoadMoreRecyclerView extends RecyclerView {
    private static final int LINEAR_LM = 0;
    private static final int GRID_LM = 1;
    private static final int STAGGERED_GRID_LM = 2;
    private int layoutManagerType;

    private boolean loading = false;
    private int visibleThreshold = 0;

    private OnLoadMoreListener onLoadMoreListener;

    private boolean isDrawLineEnabled = false;
    private DrawLineViewHelper mDrawLineViewHelper;

    @Nullable
    protected HeaderFooterCementAdapter headerFooterCementAdapter;

    public LoadMoreRecyclerView(Context context) {
        this(context, null, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (LinearLayoutManager.class.isInstance(layout)) {
            layoutManagerType = LINEAR_LM;
        } else if (GridLayoutManager.class.isInstance(layout)) {
            layoutManagerType = GRID_LM;
        } else if (StaggeredGridLayoutManager.class.isInstance(layout)) {
            layoutManagerType = STAGGERED_GRID_LM;
        } else {
            throw new IllegalArgumentException(String.format("layout[%s] is not supported",
                    layout.getClass().getSimpleName()));
        }
        super.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter == null || HeaderFooterCementAdapter.class.isInstance(adapter)) {
            headerFooterCementAdapter = (HeaderFooterCementAdapter) adapter;
        }
        super.setAdapter(adapter);
    }

    private boolean isAdapterHasMore() {
        return headerFooterCementAdapter == null || headerFooterCementAdapter.hasMore();
    }

    private void init(Context context, AttributeSet attrs) {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    boolean canLoadMore = false;
                    if (layoutManagerType == STAGGERED_GRID_LM) {
                        int totalItemCount = getLayoutManager().getItemCount();
                        int[] lastVisibleItems = ((StaggeredGridLayoutManager) getLayoutManager())
                                .findLastVisibleItemPositions(null);

                        if (lastVisibleItems != null && lastVisibleItems.length > 0) {
                            for (int i = 0; i < lastVisibleItems.length; i = i + 1) {
                                if (lastVisibleItems[i] == totalItemCount - 1 - visibleThreshold) {
                                    canLoadMore = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        int visibleItemCount = getLayoutManager().getChildCount();
                        int totalItemCount = getLayoutManager().getItemCount();
                        int firstVisibleItem = 0;
                        switch (layoutManagerType) {
                            case LINEAR_LM: {
                                firstVisibleItem = ((LinearLayoutManager) getLayoutManager())
                                        .findFirstVisibleItemPosition();
                                break;
                            }
                            case GRID_LM: {
                                firstVisibleItem = ((GridLayoutManager) getLayoutManager())
                                        .findFirstVisibleItemPosition();
                                break;
                            }
                        }
                        canLoadMore = (totalItemCount - visibleItemCount)
                                <= (firstVisibleItem + visibleThreshold);
                    }
                    if (!loading && isAdapterHasMore() && canLoadMore
                            && onLoadMoreListener != null) {
                        // End has been reached
                        setLoadMoreStart();
                        onLoadMoreListener.loadMore();
                    }
                }

            }
        });

        mDrawLineViewHelper = new DrawLineViewHelper();
        mDrawLineViewHelper.init(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isDrawLineEnabled) {
            /**
             * 绘制分割线
             */
            if (mDrawLineViewHelper != null) {
                mDrawLineViewHelper.onDrawLine(this, canvas);
            }
        }
    }

    public void setDrawLineEnabled(boolean drawLineEnabled) {
        this.isDrawLineEnabled = drawLineEnabled;
    }

    public void setDrawLine(boolean left, boolean top, boolean right, boolean bottom) {
        if (mDrawLineViewHelper != null) {
            mDrawLineViewHelper.setDrawLine(left, top, right, bottom);
            invalidate();
        }
    }

    public boolean isLoading() {
        return loading;
    }

    @Deprecated
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setLoadMoreStart() {
        loading = true;
        if (headerFooterCementAdapter != null) {
            headerFooterCementAdapter.setLoadMoreState(CementLoadMoreModel.START);
        }
    }

    public void setLoadMoreComplete() {
        loading = false;
        if (headerFooterCementAdapter != null) {
            headerFooterCementAdapter.setLoadMoreState(CementLoadMoreModel.COMPLETE);
        }
    }

    public void setLoadMoreFailed() {
        loading = false;
        if (headerFooterCementAdapter != null) {
            headerFooterCementAdapter.setLoadMoreState(CementLoadMoreModel.FAILED);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }
}
