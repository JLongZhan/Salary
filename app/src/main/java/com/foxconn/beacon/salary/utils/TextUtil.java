package com.foxconn.beacon.salary.utils;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Author: JLow
 * Date: 2017/9/28 0028.
 * Time:17:59
 * Describe:
 */

public class TextUtil {

    public  static int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.round(fontMetrics.descent - fontMetrics.ascent);
    }

    public static int getTextWidth(Paint paint,String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }
}
