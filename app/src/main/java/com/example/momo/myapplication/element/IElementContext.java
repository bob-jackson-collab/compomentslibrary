package com.example.momo.myapplication.element;

import android.content.Context;

import com.example.momo.myapplication.element.Element;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
public interface IElementContext {

    Context getContext();

    /**
     * 获取ElementManager中注册的Element对象
     * @param cls
     * @param <T>
     * @return
     */
    <T extends Element> T getElement(Class<T> cls);
}
