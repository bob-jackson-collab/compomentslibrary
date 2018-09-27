package com.example.momo.myapplication.permission;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.List;

public class FragmentPermissionChecker {


    private Fragment mFragment;

    private Context mContext;

    private PermissionListener mPermissionListener;

    public FragmentPermissionChecker(Context context, Fragment fragment, PermissionListener listener) {
        this.mContext = context;
        this.mFragment = fragment;
        this.mPermissionListener = listener;
    }

    /**
     * 同时请求多个权限, 会先检测是否需要提示权限用途。
     */
    public boolean requestPermission(String[] permissions, int requestCode) {
        List<String> needGrantPermissions = PermissionUtil.getInstance().checkSelfPermission(mContext, permissions);
        if (needGrantPermissions != null && needGrantPermissions.size() > 0) {
            PermissionUtil.getInstance().requestPermission(mFragment, needGrantPermissions.toArray(new String[0]), requestCode);
            return false;
        }

        return true;
    }

    public boolean checkPermission(String[] permissions) {
        List<String> needGrantPermissions = PermissionUtil.getInstance().checkSelfPermission(mContext, permissions);
        if (needGrantPermissions != null && needGrantPermissions.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 请求单个权限,先检测是否提示权限用途
     */
    public boolean requestPermission(String permission, int requestCode) {
        boolean hasPermission = PermissionUtil.getInstance().checkSelfPermission(mContext, permission);

        if (!hasPermission) {
            PermissionUtil.getInstance().requestPermission(mFragment, permission, requestCode);
        }

        return hasPermission;
    }

    /**
     * 检测请求权限结果
     */
    public void checkPermissionResult(int requestCode, int[] grantResults) {
        if (mPermissionListener == null) {
            return;
        }
        boolean success = PermissionUtil.getInstance().verifyPermissions(grantResults);
        if (success) {
            mPermissionListener.onPermissionGranted(requestCode);
        } else {
            mPermissionListener.onPermissionDenied(requestCode);
        }
    }
}
