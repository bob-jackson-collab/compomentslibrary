package com.example.momo.myapplication.ticker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.momo.myapplication.BounceInAnimator;
import com.example.momo.myapplication.ParticleView;
import com.example.momo.myapplication.R;
import com.robinhood.ticker.TickerView;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends BaseActivity {
    private final String alphabetlist = "abcdefghijklmnopqrstuvwxyz";

    private TickerView ticker1, ticker2, ticker3;

    private TickerView ticker4, ticker5;

    private ParticleView mParticleView;
    private RelativeLayout mRl;

    private ImageView mIvOne;
    private ImageView mIvTwo;
    private ImageView mIvThree;
    private ImageView mIvFour;
    private ImageView mIvFive;
    private ImageView mIvSix;
    private ImageView mIvCenter;

    private AnimatorSet mAnimatorSetOne;
    private AnimatorSet mAnimatorSetTwo;
    private AnimatorSet mAnimatorSetSix;
    private AnimatorSet mAnimatorSetFour;
    private AnimatorSet mAnimatorSetFive;

    private float mRoation = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticker);

        ticker1 = findViewById(R.id.ticker1);
        ticker2 = findViewById(R.id.ticker2);
        ticker3 = findViewById(R.id.ticker3);
        ticker4 = findViewById(R.id.text1);
        ticker5 = findViewById(R.id.text2);
        mRl = findViewById(R.id.rl);

        mParticleView = findViewById(R.id.particleView);
        mParticleView.start(true);

        mIvCenter = (ImageView) findViewById(R.id.similarity_module_iv_center);

        mIvOne = (ImageView) findViewById(R.id.similarity_module_iv_guide_one);
        mIvTwo = (ImageView) findViewById(R.id.similarity_module_iv_guide_two);
        mIvThree = (ImageView) findViewById(R.id.similarity_module_iv_guide_three);
        mIvFour = (ImageView) findViewById(R.id.similarity_module_iv_guide_four);
        mIvFive = (ImageView) findViewById(R.id.similarity_module_iv_guide_five);
        mIvSix = (ImageView) findViewById(R.id.similarity_module_iv_guide_six);

        findViewById(R.id.perfBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAnimatorSet();
                executeImageCenterAnim();
                mIvTwo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnimatorSetTwo.start();
                    }
                }, 30);

                mIvSix.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnimatorSetSix.start();
                    }
                }, 60);
                mIvFive.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnimatorSetFive.start();
                    }
                }, 120);
                mIvFour.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnimatorSetFour.start();
                    }
                }, 180);

                mIvOne.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnimatorSetOne.start();
                    }
                }, 240);


                mRl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BounceInAnimator bounce = new BounceInAnimator();
                        bounce.setDuration(400);
                        bounce.setInterpolator(new AnticipateInterpolator());
                        bounce.getAnimatorAgent().playTogether(
                                ObjectAnimator.ofFloat(mRl, "scaleX", 1.1f, 0.9f),
                                ObjectAnimator.ofFloat(mRl, "scaleY", 1.1f, 0.9f)
                        );
                        bounce.start();
                    }
                }, 300);
            }
        });

    }

    private void executeImageCenterAnim() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mIvCenter, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mIvCenter, "scaleY", 0.0f, 1.0f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mIvThree, "rotation", 0f, 23f);
        ObjectAnimator ivThreeScaleX = ObjectAnimator.ofFloat(mIvThree, "scaleX", 0.0f, 1.0f);
        ObjectAnimator ivThreeScaleY = ObjectAnimator.ofFloat(mIvThree, "scaleY", 0.0f, 1.0f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mIvThree, "translationX", -(mIvThree.getLeft() + mIvThree.getWidth() / 2
                - mIvCenter.getLeft() - mIvCenter.getWidth() / 2), 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mIvThree, "translationY", -(mIvThree.getTop() + mIvThree.getHeight() / 2
                - mIvCenter.getTop() - mIvCenter.getHeight() / 2), 0);
        ObjectAnimator ivAlpha = ObjectAnimator.ofFloat(mIvThree, "alpha", 0.1f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
        animatorSet.setDuration(500);
        animatorSet.playTogether(scaleX, scaleY, ivThreeScaleX, ivThreeScaleY, translationX, translationY, rotation, ivAlpha);
        animatorSet.start();
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mAnimatorSetOne.start();
//            }
//        });
    }

    private AnimatorSet addAndExecuteAnimator(ImageView imageView, float rotation) {
        mRoation = rotation;
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(imageView, "rotation", 0f, mRoation);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(imageView, "alpha", 0.1f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(imageView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(imageView, "scaleY", 0.0f, 1.0f);
        ObjectAnimator tranXAnim;
        ObjectAnimator tranYAnim;

        tranXAnim = ObjectAnimator.ofFloat(imageView, "translationX", mIvCenter.getLeft() + mIvCenter.getWidth() / 2
                - imageView.getLeft() - imageView.getWidth() / 2, 0);
        tranYAnim = ObjectAnimator.ofFloat(imageView, "translationY", mIvCenter.getTop() + mIvCenter.getHeight() / 2
                - imageView.getTop()  - imageView.getHeight() / 2, 0);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(560);
//        animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
        animatorSet.playTogether(rotationAnim, scaleXAnim, scaleYAnim, tranXAnim, tranYAnim, alphaAnim);
        return animatorSet;
    }


    private void initAnimatorSet() {
        mAnimatorSetOne = addAndExecuteAnimator(mIvOne, -20f);
        mAnimatorSetTwo = addAndExecuteAnimator(mIvTwo, -2f);
        mAnimatorSetFour = addAndExecuteAnimator(mIvFour, 7f);
        mAnimatorSetFive = addAndExecuteAnimator(mIvFive, -30f);
        mAnimatorSetSix = addAndExecuteAnimator(mIvSix, 16f);
    }

    @Override
    protected void onUpdate() {
        final int digits = RANDOM.nextInt(2) + 6;

        ticker1.setText("1234568");
        ticker1.animate().translationX(100);
        final String currencyFloat = Float.toString(RANDOM.nextFloat() * 100);
        ticker2.setText("$" + currencyFloat.substring(0, Math.min(digits, currencyFloat.length())));
        ticker3.setText(generateChars(RANDOM, alphabetlist, digits));
    }

    private String generateChars(Random random, String list, int numDigits) {
        final char[] result = new char[numDigits];
        for (int i = 0; i < numDigits; i++) {
            result[i] = list.charAt(random.nextInt(list.length()));
        }
        return new String(result);
    }
}