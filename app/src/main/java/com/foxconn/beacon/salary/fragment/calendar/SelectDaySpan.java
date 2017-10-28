package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.foxconn.beacon.salary.base.BaseApplication;
import com.foxconn.beacon.salary.utils.PixelUtil;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class SelectDaySpan implements LineBackgroundSpan {
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //消除锯齿
        paint.setStyle(Paint.Style.STROKE);//绘制空心圆或 空心矩形
        //绘制圆环
        paint.setColor(Color.parseColor("#cccccc"));
        int lineWidth = PixelUtil.dp2px(BaseApplication.sContext, 1);
        paint.setStrokeWidth(lineWidth);
        c.drawCircle((right - left) / 2, (bottom - top) / 2 /*+ CircleBackGroundSpan.dip2px(4)*/,
                /*CircleBackGroundSpan.dip2px(20),*/
                right / 2 - lineWidth,
                paint);
    }
}
