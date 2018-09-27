package com.example.momo.myapplication.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 16/1/22.
 * <p/>
 * Momo Tech 2011-2016 © All Rights Reserved.
 */
public final class PermissionUtil {
    private static PermissionUtil ourInstance = new PermissionUtil();

    public static PermissionUtil getInstance() {
        return ourInstance;
    }

    private PermissionUtil() {
    }

    /**
     * 判断android版本是否大于23
     */
    public boolean isNeedCheckPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 检查是否已经获取到指定权限
     */
    public boolean checkSelfPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检查是否获取到指定权限列表里的权限
     *
     * @return 未获取的权限列表
     */
    public List<String> checkSelfPermission(Context context, String[] permissions) {
        // NOTE Android M 以下系统避免ContextCompat兼容问题，直接给权限通过。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return null;
        }

        List<String> needGrantPermissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            boolean hasPermission = checkSelfPermission(context, permissions[i]);
            if (!hasPermission) {
                needGrantPermissionList.add(permissions[i]);
            }
        }

        return needGrantPermissionList;
    }

    /**
     * 是否需要展示权限获取说明
     *
     * @return 第一次请求或者用户关闭权限时返回true
     */
    public boolean shouldShowRequestPermissionRationale(Activity context, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, permission);
    }

    /**
     * 是否需要展示权限获取说明
     *
     * @return 第一次请求或者用户关闭权限时返回true
     */
    public boolean shouldShowRequestPermissionRationale(Fragment context, String permission) {
        return context.shouldShowRequestPermissionRationale(permission);
    }

    /**
     * 请求指定权限列表
     *
     * @param permissions 权限列表
     */
    public void requestPermission(Activity context, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(context, permissions, requestCode);
    }

    /**
     * 请求指定权限列表
     *
     * @param permissions 权限列表
     */
    public void requestPermission(Fragment context, String[] permissions, int requestCode) {
        context.requestPermissions(permissions, requestCode);
    }

    /**
     * 请求指定单个权限
     */
    public void requestPermission(Activity context, String permission, int requestCode) {
        requestPermission(context, new String[]{permission}, requestCode);
    }

    /**
     * 请求指定单个权限
     */
    public void requestPermission(Fragment context, String permission, int requestCode) {
        requestPermission(context, new String[]{permission}, requestCode);
    }

    /**
     * Checks all given permissions have been granted.
     *
     * @param grantResults results
     * @return returns true if all permissions have been granted.
     */
    public boolean verifyPermissions(int... grantResults) {
        boolean granted = true;
        if (grantResults != null && grantResults.length > 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
        } else {
            granted = false;
        }
        return granted;
    }

    public List<String> verifyPermissions(String[] permissions, int[] grantResults) {
        List<String> deniedPermissions = null;
        if (grantResults != null) {
            deniedPermissions = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i]);
                }
            }
        }
        return deniedPermissions;
    }

    /**
     * 单个权限引导提示文案
     */
    public String getPermissionGuideText(String permission) {
        String rationale = "";
        switch (permission) {
            case Manifest.permission.RECORD_AUDIO:
                rationale = "陌陌需使用麦克风权限，以正常使用语音、视频、直播等功能。\n\n请在设置-应用-陌陌-权限中开启麦克风权限。";
                break;
            case Manifest.permission.READ_CONTACTS:
                rationale = "需开启通讯录权限，仅用于查看你的好友。\n\n请在设置-应用-陌陌-权限中开启通讯录权限。";
                break;
            case Manifest.permission.READ_SMS:
                rationale = "需使用短信权限以读取验证码，陌陌不会发送短信给他人。\n\n请在设置-应用-陌陌-权限中开启短信权限。";
                break;
            case Manifest.permission.CAMERA:
                rationale = "陌陌需使用相机权限，以正常使用拍照、视频、直播等功能。\n\n请在设置-应用-陌陌-权限中开启相机权限。";
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
                rationale = "陌陌需使用地理位置权限，才能展示附近的人或动态。\n\n请在设置-应用-陌陌-权限中开启位置权限。";
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                rationale = "陌陌需使用存储权限，以保证聊天信息的安全性。\n\n请在设置-应用-陌陌-权限中开启存储权限。";
                break;
            default:
                break;
        }

        return rationale;
    }

    /**
     * 权限引导title
     */
    public String getPermissionGuideTitle(String permission) {
        String title = "";
        switch (permission) {
            case Manifest.permission.RECORD_AUDIO:
                title = "\"麦克风\"权限申请";
                break;
            case Manifest.permission.READ_CONTACTS:
                title = "\"通讯录\"权限申请";
                break;
            case Manifest.permission.READ_SMS:
                title = "\"短信\"权限申请";
                break;
            case Manifest.permission.CAMERA:
                title = "\"相机\"权限申请";
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
                title = "\"位置\"权限申请";
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                title = "\"存储\"权限申请";
                break;
            default:
                break;
        }
        return title;
    }
}
