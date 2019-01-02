package com.example.momo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.momo.myapplication.viewpager.AutoScrollViewPager;
import com.example.momo.myapplication.viewpager.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/27
 *   desc: MyApplication
 * </pre>
 */
public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        List<String> data = new ArrayList<>();
        data.add("11111");
        data.add("22222");
        data.add("333333");
//        AutoScrollViewPager mViewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
//        mViewPager.setStopWhenTouch(false);
//        MyAdapter mAdapter = new MyAdapter(data);
//        mViewPager.setAdapter(mAdapter);
//        // optional start auto scroll
//        mViewPager.startAutoScroll();
    }
}
