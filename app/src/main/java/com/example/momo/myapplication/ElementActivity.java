package com.example.momo.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.momo.myapplication.element.Element;
import com.example.momo.myapplication.element.ElementManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
public class ElementActivity extends AppCompatActivity {

    private ElementManager mElementManager;
    private List<Element> mElements = new ArrayList<>();
    private UserElement mUserElement;
    private View userView;
    private List<TextModel> mTextModels = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);
        userView = findViewById(R.id.frame);
        mUserElement = new UserElement<>(userView);
        mElements.add(mUserElement);
        mElementManager = new ElementManager(this, mElements);
        mElementManager.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void requestData(){
        List<String> datas = new ArrayList<>();
        datas.add("111111111");
        datas.add("222222222");
        datas.add("333333333");
        datas.add("444444444");

        for (String data : datas) {
            TextModel textModel = new TextModel(data);
            mTextModels.add(textModel);
        }

        mUserElement.setDatas(mTextModels);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mElementManager.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mUserElement.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
