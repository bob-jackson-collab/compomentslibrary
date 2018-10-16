package com.example.momo.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/29
 *   desc: MyApplication
 * </pre>
 */
public class ParticleView extends View {

    private static final String TAG = "HeartView";

    private static final int DEFAULT_DROP_LOOK_NUM = 10;

    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Bitmap mBitmap3;
    private Bitmap mBitmap4;
    private Matrix mMatrix = new Matrix();
    List<Particle> mParticles = new ArrayList<>();
    private Paint mPaint = new Paint();

    private boolean isRun;

    public ParticleView(Context context) {
        this(context, null);
    }

    public ParticleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewThread")
    public ParticleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = 400;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = 400;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRun) {
            canvas.save();
            for (int i = 0; i < DEFAULT_DROP_LOOK_NUM; i++) {
                Particle particle = mParticles.get(i);
                mMatrix.setTranslate(-particle.getWidth() / 2, -particle.getHeight() / 2);
                mMatrix.postRotate(particle.getRotation());
                mMatrix.postTranslate(particle.getWidth() / 2 + particle.getX(), particle.getHeight() / 8 + particle.getY());
                canvas.drawBitmap(particle.getBitmap(), mMatrix, mPaint);
                particle.setY(particle.getY() + particle.getySpeed());
                // 如果超过了屏幕
                if (particle.getY() > getHeight()) {
                    particle.setY((float) (0 - Math.random() * particle.getHeight()));
                }

                particle.setRotation(particle.getRotation() + particle.getRotationSpeed());
            }
            canvas.restore();
            invalidate();
        }
    }

    public void start(boolean isRun) {
        this.isRun = isRun;
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        if (mParticles.size() == 0) {
            for (int i = 0; i < DEFAULT_DROP_LOOK_NUM; ++i) {
                if (i % 3 == 0) {
                    mParticles.add(ParticleFactory.createHeart(720, 1080, mBitmap1));
                } else {
                    mParticles.add(ParticleFactory.createHeart(720, 1080, mBitmap2));
                }
            }
        }
        invalidate();
    }

    public void stop() {
        isRun = false;
        invalidate();
    }

    /**
     * 释放资源
     */
    private void release() {
        if (mParticles != null && mParticles.size() > 0) {
            for (Particle particle : mParticles) {
                if (!particle.getBitmap().isRecycled()) {
                    particle.getBitmap().recycle();
                }
            }
            mParticles.clear();
        }
    }
}
