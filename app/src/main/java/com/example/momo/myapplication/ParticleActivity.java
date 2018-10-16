package com.example.momo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/29
 *   desc: MyApplication
 * </pre>
 */
public class ParticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle);

        final TextView textView = findViewById(R.id.tv);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setAlpha(0);
                textView.animate().scaleX(0.5f);
                textView.animate().scaleY(0.5f);
                textView.animate().alpha(100).setDuration(300);
                textView.animate().translationY(400);

            }
        });

        ViewGroup content = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup vg = (ViewGroup) ((ViewGroup) content.getChildAt(0)).getChildAt(1);
        content.addView(new ParticleView(this));
    }
}
