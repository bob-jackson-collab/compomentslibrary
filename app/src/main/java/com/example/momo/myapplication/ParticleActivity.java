package com.example.momo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/29
 *   desc: MyApplication
 * </pre>
 */
public class ParticleActivity extends AppCompatActivity {

    public static final int MAX_COUNT = 10;
    private EditText mEditText;
    private TextWatcher mTextWatcher;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle);
        mEditText = findViewById(R.id.editText);
        mTextWatcher = new MyTextWatcher();
        mEditText.addTextChangedListener(mTextWatcher);
        mEditText.setFilters(new InputFilter[]{});
        mTextView = findViewById(R.id.text);


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


    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.e("editTextYs", "beforeTextChanged-------" + s.toString() + "；start:" + start + "；count:" + count + "；after:" + after
                    + "； getSelectionStart:" + mEditText.getSelectionStart() + "； getSelectionEnd:" + mEditText.getSelectionEnd());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e("editTextYs", "beforeTextChanged-------" + s.toString() + "；start:" + start + "；count:" + count + "；before:" + before
                    + "； getSelectionStart:" + mEditText.getSelectionStart() + "； getSelectionEnd:" + mEditText.getSelectionEnd());
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.e("editTextYs", "beforeTextChanged-------" + s.toString() + "； getSelectionStart:" + mEditText.getSelectionStart() + "； getSelectionEnd:" + mEditText.getSelectionEnd());
            int editStart = mEditText.getSelectionStart();
            int editEnd = mEditText.getSelectionEnd();
            // 先去掉监听器，否则会出现栈溢出
            mEditText.removeTextChangedListener(mTextWatcher);

            if (editEnd == s.length() && editStart == s.length()) {
                // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
                // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
                while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                    s.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            } else {
                while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                    s.delete(s.length() - 1, s.length());
                }
            }


            // mEditText.setText(s);将这行代码注释掉就不会出现后面所说的输入法在数字界面自动跳转回主界面的问题了，多谢@ainiyidiandian的提醒
            mEditText.setSelection(editStart);
            // 恢复监听器
            mEditText.addTextChangedListener(mTextWatcher);
            setLeftCount();
        }
    }

    /**
     * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
     */
    private void setLeftCount() {
        mTextView.setText(String.valueOf((MAX_COUNT - getInputCount())));
    }

    /**
     * 获取用户输入的分享内容字数
     */
    private long getInputCount() {
        return calculateLength(mEditText.getText().toString());
    }

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     */
    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }
}
