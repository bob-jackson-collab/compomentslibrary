package com.example.momo.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momo.myapplication.cenment.CementAdapter;
import com.example.momo.myapplication.element.Element;
import com.example.momo.myapplication.permission.PermissionChecker;
import com.example.momo.myapplication.permission.PermissionListener;
import com.example.momo.myapplication.permission.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

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
    private CementAdapter mCementAdapter;
    private List<TextModel> mDatas = new ArrayList<>();
    private ElementActivity mElementActivity;

    public UserElement(T view) {
        super(view);
        initView(view);
    }

    private void initView(View view) {
        mTvName = view.findViewById(R.id.tv_name);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mTvName.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate() {
        super.onCreate();
        mElementActivity = (ElementActivity) getContext();
        mPermissionChecker = new PermissionChecker(mElementActivity, this);
        mCementAdapter = new CementAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mCementAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_name:
                Toast.makeText(getContext(), TAG, Toast.LENGTH_SHORT).show();
                mTvName.setText(TAG);
                if (!PermissionUtil.getInstance().checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                    mPermissionChecker.requestPermission(Manifest.permission.CAMERA, REQUEST_CODE);
                }
                mElementActivity.requestData();
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setDatas(List<TextModel> datas) {
        this.mDatas = datas;
        mCementAdapter.replaceAllModels(mDatas);
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
