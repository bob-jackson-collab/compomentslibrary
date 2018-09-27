package com.example.momo.myapplication.element;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;

import java.util.List;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ElementManager {

    private Context mContext;
    private List<Element> mElementList;
    private ArrayMap<Class<? extends Element>, Element> mClassElementArrayMap = new ArrayMap<>();
    private IElementContext mIElementContext = new IElementContext() {
        @Override
        public Context getContext() {
            return mContext;
        }

        @Override
        public <T extends Element> T getElement(Class<T> cls) {
            return (T) mClassElementArrayMap.get(cls);
        }
    };

    public ElementManager(Context context, List<Element> elements) {
        if (elements == null || elements.isEmpty()) {
            throw new RuntimeException("elements can not be null");
        }
        this.mContext = context;
        mElementList = elements;
        for (Element element : elements) {
            mClassElementArrayMap.put(element.getClass(), element);
            element.attach(mIElementContext);
        }
    }

    public List<Element> getElements() {
        return mElementList;
    }

    public void onCreate() {
        for (Element element : mElementList) {
            element.onCreate();
        }
    }

    public void onDestroy() {
        for (Element element : mElementList) {
            element.onDestroy();
        }
    }

    public void onPause() {
        for (Element element : mElementList) {
            element.onPause();
        }
    }

    public void onResume() {
        for (Element element : mElementList) {
            element.onResume();
        }
    }
}
