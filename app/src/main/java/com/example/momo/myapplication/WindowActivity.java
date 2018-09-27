package com.example.momo.myapplication;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
public class WindowActivity extends AppCompatActivity implements View.OnTouchListener{

    Button mFloatingButton;
    WindowManager.LayoutParams mLayoutParams;
    WindowManager mWindowManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.TRANSPARENT);
        //第三个参数代表flags，第四个参数代表type
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;   //配置flags
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;  //配置type
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;   //配置gravity
        mLayoutParams.x = 400;   //相对于gravity
        mLayoutParams.y = 800;   //相对于gravity
        //将一个Button添加到屏幕为（100,300）的位置
        mFloatingButton = new Button(this);
        mFloatingButton.setText("test button");
        mFloatingButton.setOnTouchListener(this);
        mFloatingButton.setLayoutParams(mLayoutParams);
        mWindowManager.addView(mFloatingButton, mLayoutParams);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
