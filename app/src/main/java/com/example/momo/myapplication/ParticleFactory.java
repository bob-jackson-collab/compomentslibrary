package com.example.momo.myapplication;

import android.graphics.Bitmap;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/29
 *   desc: MyApplication
 * </pre>
 */
public class ParticleFactory {

    private ParticleFactory() {

    }

    public static Particle createHeart(int width, int height, Bitmap originalBitmap) {
        Particle particle = new Particle();
        if (originalBitmap == null) {
            throw new NullPointerException("originalBitmap cannot be null");
        }
        particle.setWidth(originalBitmap.getWidth());
        particle.setHeight(originalBitmap.getHeight());
        // 设置起始位置X坐标
        particle.setX((float) Math.random() * (width - particle.getWidth()));
        // 设置起始位置Y坐标
        particle.setY((float) Math.random() * (height - particle.getHeight()));
        // 设置速度
        particle.setySpeed((float) Math.random() * 4);
        // 设置旋转角度
        particle.setRotation((float) Math.random() * 180 - 90);
        // 设置旋转速度
        particle.setRotationSpeed((float) Math.random() * 5);
        particle.setBitmap(originalBitmap);
        return particle;
    }
}
