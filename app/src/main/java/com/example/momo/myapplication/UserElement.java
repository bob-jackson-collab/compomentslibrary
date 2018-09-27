package com.example.momo.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momo.myapplication.element.Element;
import com.example.momo.myapplication.permission.PermissionChecker;
import com.example.momo.myapplication.permission.PermissionListener;
import com.example.momo.myapplication.permission.PermissionUtil;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
public class UserElement<T extends View> extends Element<T> implements View.OnClickListener, PermissionListener {

    public static final int REQUEST_CODE = 0x1111;
    private static final String TAG = "UserElement";
    private TextView mTvName;
    private RecyclerView mRecyclerView;
    private PermissionChecker mPermissionChecker;

    public UserElement(T view) {
        super(view);
        initView(view);
    }

    private void initView(View view) {
        mTvName = view.findViewById(R.id.tv_name);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mTvName.setOnClickListener(this);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mPermissionChecker = new PermissionChecker((Activity) getContext(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_name:
                Toast.makeText(getContext(), TAG, Toast.LENGTH_SHORT).show();
                mTvName.setText(TAG);
                if (!PermissionUtil.getInstance().checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                    mPermissionChecker.requestPermission(Manifest.permission.CAMERA, REQUEST_CODE);
                }
                break;
            default:
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionChecker.checkPermissionResult(requestCode, grantResults);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        if (requestCode == REQUEST_CODE) {
            System.out.println("camera");
        }
    }

    @Override
    public void onPermissionDenied(int requestCode) {

    }

    @Override
    public void onPermissionCanceled(int requestCode) {

    }
}
