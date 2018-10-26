package com.example.momo.myapplication.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;

public class MyScrollToByLayout extends ViewGroup {

    private Button btn1, btn2;
    private Scroller mScroller;

    public MyScrollToByLayout(Context context) {
        super(context);
    }

    public MyScrollToByLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollToByLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        btn1 = new Button(context);
        btn2 = new Button(context);            //新建两个Button实例btn1,btn2
        mScroller = new Scroller(context);
        btn1.setText("Button1");
        btn2.setText("Button2");

        addView(btn1);
        addView(btn2);                        //将两个Button添加到当前layout中

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mScroller.startScroll(getScrollX(), getScrollY(), -(int) btn2.getX(), -(int) btn2.getY(), 10000);
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int spec) {
        int mode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);

        int height = 0;
        if (mode == MeasureSpec.AT_MOST) {
            throw new IllegalStateException("Must not be" +
                    "MeasureSpec.AT_MOST");        //layout中的控件高度不能指定为wrap_content
        } else {
            height = size;
        }
        return height;
    }

    private int measureWidth(int spec) {
        int mode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);

        int width = 0;
        if (mode == MeasureSpec.AT_MOST) {
            throw new IllegalStateException("Must not be" +
                    "MeasureSpec.AT_MOST");        //layout中的控件宽度不能指定为wrap_content
        } else {
            width = size;
        }
        return width;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View tn1 = getChildAt(0);
        int x2 = 400;
        int x1 = tn1.getMeasuredWidth() + x2;
        tn1.layout(-x1, 500, -x2, tn1.getMeasuredHeight() + 500);    //将第一个button放置在指定位置
        View tn2 = getChildAt(1);
        tn2.layout(x2, 500, x1, tn2.getMeasuredHeight() + 500);        //将第二个button放置在指定位置
    }
}