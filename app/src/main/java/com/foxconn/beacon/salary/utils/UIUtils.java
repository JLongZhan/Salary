package com.foxconn.beacon.salary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseApplication;

/**
 * @author: F1331886
 * @date: 2017/10/31 0031.
 * @describe:
 */

public class UIUtils {
    public static Context getContext() {
        return BaseApplication.sContext;
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取资源文件中的数组
     *
     * @param resId
     * @return
     */
    public static String[] getArrays(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取资源文件中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    public static int getDimens(int resId) {
        float dimension = getResources().getDimension(resId);
        return (int) (dimension + 0.5);
    }

    /**
     * 返回资源文件中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResources().getString(resId
        );
    }


    /**
     * 初始化窗口
     */
    public static PopupWindow initPopupWindow(final Activity activity, View view) {

        PopupWindow mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1f;
                window.setAttributes(attributes);
            }
        });
        //使用默認的動畫樣式
        mPopupWindow.setAnimationStyle(R.style.animTranalate);
        return mPopupWindow;
    }

}
