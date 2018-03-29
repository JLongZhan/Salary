package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.foxconn.beacon.salary.base.BaseApplication;
import com.foxconn.beacon.salary.model.OverTimeBean;
import com.foxconn.beacon.salary.utils.PixelUtil;
import com.foxconn.beacon.salary.utils.TextUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class OvertimeSpan implements LineBackgroundSpan {

    private Paint mPaint;
    private List<OverTimeBean> mOverTimeBeens;

    public OvertimeSpan() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOverTimeBeens = DataSupport.findAll(OverTimeBean.class);
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {

        p.setStyle(Paint.Style.STROKE);
        c.drawRect(left,-getTextHeight(mPaint),right-1,bottom + getTextHeight(mPaint),p);

        mPaint.setColor(0xffD81F26);
        c.drawRect(left, bottom, right - 1, bottom + getTextHeight(mPaint) * 2, mPaint);
        mPaint.setColor(0xffffffff);
        mPaint.setTextSize(14);
//        String hours = String.valueOf(mOverTimeBeens.get(Integer.parseInt(text.toString())-1).getOvertimeHours());
       String  hours = "2.00 h";
        c.drawText(hours,
                (left + right) / 2 - TextUtil.getTextWidth(mPaint, hours) / 2, bottom + getTextHeight(mPaint), mPaint);
    }


    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent + 0.5);
    }
}
