package com.example.momo.myapplication.permission;

import android.app.Activity;

import java.util.List;

/**
 *
 */
public class PermissionChecker {

    private Activity mActivity;
    private PermissionListener mPermissionListener;

    public PermissionChecker(Activity context, PermissionListener listener) {
        this.mActivity = context;
        this.mPermissionListener = listener;
    }

    /**
     * 同时请求多个权限, 会先检测是否需要提示权限用途。
     */
    public boolean requestPermission(String[] permissions, int requestCode) {
        // 获取未获得权限的列表
        List<String> needGrantPermissions = PermissionUtil.getInstance().checkSelfPermission(mActivity, permissions);
        if (needGrantPermissions != null && needGrantPermissions.size() > 0) {
            PermissionUtil.getInstance().requestPermission(mActivity, needGrantPermissions.toArray(new String[0]), requestCode);
            return false;
        }
        return true;
    }

    /**
     * 请求单个权限,先检测是否提示权限用途
     */
    public boolean requestPermission(String permission, int requestCode) {
        boolean hasPermission = PermissionUtil.getInstance().checkSelfPermission(mActivity, permission);
        if (!hasPermission) {
            PermissionUtil.getInstance().requestPermission(mActivity, permission, requestCode);
        }

        return hasPermission;
    }

    /**
     * 检测请求权限结果
     */
    public void checkPermissionResult(int requestCode, int[] grantResults) {
        if (grantResults == null || grantResults.length == 0) {
            return;
        }
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
