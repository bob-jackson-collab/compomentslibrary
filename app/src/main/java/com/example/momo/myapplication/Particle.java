package com.example.momo.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.WindowManager;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/29
 *   desc: MyApplication
 * </pre>
 */
public class Particle {

    // x轴坐标
    private float x;
    // y轴坐标
    private float y;
    private int width;
    private int height;
    // 初始旋转速度
    private float rotation;
    // 下落速度
    private float ySpeed;

    //    private float xSpeed;
    // 旋转方向
    private int rotationDirection;
    //    // 平移方向
//    private int direction;
    // 旋转速度
    private float rotationSpeed;
    private Bitmap mBitmap;
    private Context mContext;


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getRotationDirection() {
        return rotationDirection;
    }

    public void setRotationDirection(int rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
