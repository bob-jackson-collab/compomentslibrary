package com.example.momo.myapplication.element;

import android.content.Context;
import android.view.View;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 *
 *   一个比fragment还小的view单元
 * </pre>
 */
public class Element<T extends View> {

    private T mView;
    private IElementContext mIElementContext;
    private boolean mIsDestroyed;

    public Element(T view) {
        if (view == null) {
            throw new RuntimeException("view can not be null");
        }
        mView = view;
    }


    void attach(IElementContext elementContext) {
        this.mIElementContext = elementContext;
    }

    /**
     * 获取当前ElementManager里注册的其他element对象
     * @param eleClass 其他element的class对象
     * @param <N> Element类型
     * @return element对象
     */
    public <N extends Element> N getElement(Class<N> eleClass) {
        return mIElementContext.getElement(eleClass);
    }
    /**
     * 根据ID查找View
     * @param id id
     * @return view对象
     */
    public View findViewById(int id) {
        return mView.findViewById(id);
    }

    protected Context getContext() {
        return mIElementContext.getContext();
    }

    public T getView() {
        return mView;
    }

    protected void onCreate() {

    }

    protected void onDestroy() {
        this.mIsDestroyed = true;
    }

    public boolean isDestroy() {
        return mIsDestroyed;
    }

    protected void onPause() {

    }

    protected void onResume() {

    }

}
