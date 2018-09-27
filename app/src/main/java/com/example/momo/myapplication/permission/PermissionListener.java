package com.example.momo.myapplication.permission;

/**
 * 权限授权结果interface
 */
public interface PermissionListener {
    /**
     * 请求权限被授权
     */
    void onPermissionGranted(int requestCode);

    /**
     * 请求权限未被用户授权
     */
    void onPermissionDenied(int requestCode);

    /**
     * 请求权限操作取消了
     */
    void onPermissionCanceled(int requestCode);
}