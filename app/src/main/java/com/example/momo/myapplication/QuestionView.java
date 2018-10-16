package com.example.momo.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/27
 *   desc: MyApplication
 * </pre>
 */
public class QuestionView extends View {

    private Paint mRectPaint;
    private Paint mTextPaint;

    private int height;
    private int width;

    public QuestionView(Context context) {
        super(context);
    }

    public QuestionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mRectPaint = new Paint();
        mRectPaint.setColor(getResources().getColor(R.color.colorAccent));
        mRectPaint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(0, 0, 500, 200, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        mRectPaint.setShader(shader);


        mTextPaint = new Paint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setStrokeWidth(2f);
        mTextPaint.setTextSize(60f);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        RectF rectF = new RectF(width / 2 - 200, 0, width / 2 + 200 , 200);
        canvas.drawRoundRect(rectF, 100, 100, mRectPaint);
        String text = "hello word";
        canvas.restore();
        canvas.save();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, 20, 10, mTextPaint);
        canvas.restore();
        canvas.save();

        int left = bitmap.getWidth();
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        float h = bounds.bottom - bounds.top;
        canvas.drawText(text, 0, text.length(), (left + 20), rectF.height() / 2 + h / 2, mTextPaint);
        canvas.restore();

        RectF rectF1 = new RectF(40, 150, 540, 350);
        canvas.drawRoundRect(rectF1, 100, 100, mRectPaint);
        String question = "question ask suggest";
        Rect bounds1 = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds1);
        float h1 = bounds.bottom - bounds.top;
        canvas.drawText(question, 0, text.length(), 140, 150 + rectF1.height() / 2 + h1 / 2, mTextPaint);

        RectF rectF2 = new RectF(10, 300, 510, 500);
        canvas.drawRoundRect(rectF2, 100, 100, mRectPaint);
        String text3 = "text";
        Rect bounds2 = new Rect();
        mTextPaint.getTextBounds(text3, 0, text3.length(), bounds2);
        float h2 = bounds2.bottom - bounds2.top;
        canvas.drawText(text3, 0, text3.length(), 110, 300 + rectF2.height() / 2 + h2 / 2, mTextPaint);

        Path path = new Path();
        path.moveTo(260, 500);
        path.quadTo(260, 500, 230, 550);
        path.arcTo(260, 550, 270, 540, 0, 360, false);
        path.quadTo(230, 550, 300, 500);
        path.close();
        canvas.drawPath(path, mRectPaint);

    }
}
