package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class MySpan implements LineBackgroundSpan {
    private static final String TAG = "MySpan";

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //消除锯齿
        paint.setColor(Color.parseColor("#D81F26"));
        c.drawRect(left, bottom + top + 10 - getTextHeight(paint), right, bottom + top + 15, paint);
        paint.setColor(Color.WHITE);
        c.drawText("2.0h", (left + right) / 4, bottom + top + 12, paint);

    }

    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent + 0.5f);
    }
}
