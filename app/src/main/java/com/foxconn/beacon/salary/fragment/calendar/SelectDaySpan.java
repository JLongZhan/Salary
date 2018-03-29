package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.utils.PixelUtil;
import com.foxconn.beacon.salary.utils.UIUtils;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe: 选中日期的修饰
 */

public class SelectDaySpan implements LineBackgroundSpan {

    private Paint mPaint;

    public SelectDaySpan() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(UIUtils.getColor(R.color.calendar_select_day));
        mPaint.setStrokeWidth(PixelUtil.dp2px(UIUtils.getContext(), 2));
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        //绘制边框
        c.drawRect(left, top - UIUtils.getDimens(R.dimen.calendar_day_space), right - 1,
                right - UIUtils.getDimens(R.dimen.calendar_day_space), mPaint);
    }
}
