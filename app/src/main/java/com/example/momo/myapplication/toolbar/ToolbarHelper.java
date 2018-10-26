package com.example.momo.myapplication.toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.momo.myapplication.R;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/10/17
 *   desc: MyApplication
 * </pre>
 */
public class ToolbarHelper {

    private View appBarLayout;
    private Toolbar mToolbar;

    public static ToolbarHelper buildFromActivity(BaseToolbarActivity activity, View.OnClickListener backListener) {
        ToolbarHelper toolbarHelper = new ToolbarHelper();
        View appBar = activity.findViewById(R.id.appbar_id);
        View toolbarView = activity.findViewById(R.id.toolbar_id);
        if (appBar != null) {
            toolbarHelper.appBarLayout = appBar;
        }
        if (toolbarView != null) {
            Toolbar toolbar = (Toolbar) toolbarView;
            toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) activity);
            if (backListener != null) {
                toolbar.setNavigationOnClickListener(backListener);
            }
            toolbarHelper.mToolbar = toolbar;
        }
        return toolbarHelper;
    }

    @SuppressLint("ResourceType")
    public void enableNavigationButton(boolean flag, @DrawableRes int iconResId) {
        if (!flag || iconResId <= 0) {
            mToolbar.setNavigationIcon(null);
        } else {
            mToolbar.setNavigationIcon(iconResId);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener backClickListener) {
        //设置返回按钮点击事件
        if (backClickListener != null) {
            mToolbar.setNavigationOnClickListener(backClickListener);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitle(CharSequence title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    public void setSubTitle(CharSequence subTitle) {
        if (mToolbar != null) {
            mToolbar.setSubtitle(subTitle);
        }
    }

    public void clearMenus() {
        if (mToolbar != null) {
            mToolbar.getMenu().clear();
        }
    }

    /**
     * 加载一个菜单xml文件，加载时会清空已有的菜单
     */
    public void inflateMenu(@MenuRes int menuResId, @Nullable Toolbar.OnMenuItemClickListener pMenuItemClickListener) {
        if (mToolbar != null) {
            mToolbar.getMenu().clear();
            mToolbar.inflateMenu(menuResId);
            mToolbar.setOnMenuItemClickListener(pMenuItemClickListener);
        }
    }

    /**
     * 在Toolbar右边增加菜单按钮,按钮的点击事件在 {@link android.support.v7.widget.Toolbar.OnMenuItemClickListener} 中处理
     *
     * @param iconResId 按钮图标
     * @param listener  按钮单击事件，如果是在 {@link BaseToolbarActivity} 中，则不需要指定
     */
    @SuppressLint("ResourceType")
    public MenuItem addRightMenu(int itemId, CharSequence itemText, @DrawableRes int iconResId, MenuItem.OnMenuItemClickListener listener) {
        if (mToolbar != null) {
            Menu menu = mToolbar.getMenu();
            itemId = itemId == 0 ? Menu.NONE : itemId;
            MenuItem item = menu.add(Menu.NONE, itemId, Menu.NONE, itemText);
            if (listener != null) {
                item.setOnMenuItemClickListener(listener);
            }
            if (iconResId > 0) {
                item.setIcon(iconResId);
            }
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            return item;
        }
        return null;
    }

    /**
     * 设置Toolbar左侧的空白区域 默认是 16dp
     *
     * @param insetStartInPixels 单位必须是像素
     */
    public void setContentInsetStart(int insetStartInPixels) {
        if (mToolbar != null) {
            mToolbar.setContentInsetsRelative(insetStartInPixels, mToolbar.getContentInsetEnd());
        }
    }

    /**
     * 设置Toolbar右侧的空白区域 默认是 16dp
     *
     * @param insetEndInPixels 单位必须是像素
     */
    public void setContentInsetEnd(int insetEndInPixels) {
        if (mToolbar != null) {
            mToolbar.setContentInsetsRelative(mToolbar.getContentInsetEnd(), insetEndInPixels);
        }
    }


}
