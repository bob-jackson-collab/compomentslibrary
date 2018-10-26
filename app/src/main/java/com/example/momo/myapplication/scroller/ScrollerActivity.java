package com.example.momo.myapplication.scroller;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.momo.myapplication.R;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/10/17
 *   desc: MyApplication
 * </pre>
 */
public class ScrollerActivity extends AppCompatActivity {

    private ImageView mImageView;
    private ImageView mImageViewOne;
    private ImageView mImageViewTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        mImageView = findViewById(R.id.imageView);
        mImageViewOne = findViewById(R.id.image1);
        mImageViewTwo = findViewById(R.id.image2);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animImage();
                animImageOne();
                animImageTwo();
            }
        });

//        mImageView.post(new Runnable() {
//            @Override
//            public void run() {
//                animImage();
//            }
//        });
//        mImageViewOne.post(new Runnable() {
//            @Override
//            public void run() {
//                animImageOne();
//            }
//        });
//        mImageViewTwo.post(new Runnable() {
//            @Override
//            public void run() {
//                animImageTwo();
//            }
//        });
    }

    private void animImage() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mImageView, "alpha", 0.2f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(mImageView, "scaleX", 5.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(mImageView, "scaleY", 5.0f, 1.0f);
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, 30f);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(mImageView, "translationY", 500f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotationAnim, transYAnim);
        animatorSet.start();
    }

    private void animImageOne() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mImageViewOne, "alpha", 0.2f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(mImageViewOne, "scaleX", 3.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(mImageViewOne, "scaleY", 3.0f, 1.0f);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(mImageViewOne, "translationY", 500f, 0f);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(mImageViewOne, "translationX", mImageViewOne.getWidth() / 2 - 20, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(alphaAnim, scaleXAnim, scaleYAnim, transXAnim, transYAnim);
        animatorSet.start();
    }

    private void animImageTwo() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mImageViewTwo, "alpha", 0.2f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(mImageViewTwo, "scaleX", 3.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(mImageViewTwo, "scaleY", 3.0f, 1.0f);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(mImageViewTwo, "translationY", 500f, 0f);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(mImageViewTwo, "translationX", -(mImageViewTwo.getWidth() / 2 - 20), 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(alphaAnim, scaleXAnim, scaleYAnim, transXAnim, transYAnim);
        animatorSet.start();
    }
}
