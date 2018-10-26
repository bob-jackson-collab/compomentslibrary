package com.example.momo.myapplication.toolbar;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toolbar;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/10/17
 *   desc: MyApplication
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BaseToolbarActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
