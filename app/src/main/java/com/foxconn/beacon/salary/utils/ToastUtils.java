package com.foxconn.beacon.salary.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * @author Administrator
 * @date 2017/7/9
 * @desc toast 的工具类
 */
public class ToastUtils {
    private static final String TAG = "ToastUtils";
    private static Toast toast;

    private ToastUtils() {
    }

    /**
     * 给Toast赋值
     *
     * @param context
     */
    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
            View view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
            toast.setView(view);
        }
    }

    /**
     * 显示短时间的Toast
     *
     * @param context
     * @param msg     显示的文本
     */
    public static void showShort(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时间的Toast
     *
     * @param context
     * @param resId   显示文本的资源文件
     */
    public static void showShort(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时间的Toast
     *
     * @param context
     * @param msg     显示的文本
     */
    public static void showLong(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时间的Toast
     *
     * @param context
     * @param resId   显示文本的资源文件
     */
    public static void showLong(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param msg
     * @param duration
     */
    private static void showToast(Context context, CharSequence msg, int duration) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
            toast.show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param resId
     * @param duration
     */
    private static void showToast(Context context, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
            toast.show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 取消
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}