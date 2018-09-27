package com.example.momo.myapplication;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements IMainView {

    private LifecycleRegistry mLifecycleRegistry;
    private MainPresenter mMainPresenter;
    private MainViewModel mMainViewModel;

    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvContent = findViewById(R.id.tv_content);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mMainPresenter = new MainPresenter();
        mLifecycleRegistry.addObserver(mMainPresenter);

        mMainViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(MainViewModel.class);
        mMainViewModel.init();

        mMainViewModel.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTvContent.setText(s);
            }
        });

        mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.getNetData();
            }
        });
    }

    @Override
    public void start() {

    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLifecycleRegistry.removeObserver(mMainPresenter);
    }
}
