package com.example.momo.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/14
 *   desc: MyApplication
 * </pre>
 */
public class BookService extends Service{


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
