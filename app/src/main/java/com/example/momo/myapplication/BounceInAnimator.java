package com.example.momo.myapplication;

import android.animation.ObjectAnimator;
import android.view.View;

public class BounceInAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1, 1),
                ObjectAnimator.ofFloat(target,"scaleX",0.3f,1.05f,0.9f,1),
                ObjectAnimator.ofFloat(target,"scaleY",0.3f,1.05f,0.9f,1)
        );
    }
}