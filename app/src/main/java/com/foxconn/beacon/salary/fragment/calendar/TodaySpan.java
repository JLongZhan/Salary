package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.utils.PixelUtil;
import com.foxconn.beacon.salary.utils.TextUtil;
import com.foxconn.beacon.salary.utils.UIUtils;

/**
 * @author: F1331886
 * @date: 2017/11/8 0008.
 * @describe:
 */

public class TodaySpan implements LineBackgroundSpan {
    private final Paint mPaint;

    public TodaySpan() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(UIUtils.getColor(R.color.app_base_color));
        mPaint.setStrokeWidth(PixelUtil.dp2px(UIUtils.getContext(), 2));
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        p.setColor(Color.TRANSPARENT);
        c.drawRect(left, top - UIUtils.getDimens(R.dimen.calendar_day_space),
                right - 1, right - UIUtils.getDimens(R.dimen.calendar_day_space), p);
        mPaint.setTextSize(p.getTextSize());
        String today = "今天";
        c.drawText(today, (right - TextUtil.getTextWidth(mPaint, today)) / 2, UIUtils.getDimens(R.dimen.calendar_day_space) + getTextHeight(p), mPaint);
    }

    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent + 0.5);
    }
}
