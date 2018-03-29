package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.utils.PixelUtil;
import com.foxconn.beacon.salary.utils.UIUtils;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class OvertimeSpan implements LineBackgroundSpan {

    private final Paint mHammerPaint;
    private Paint mPaint;
    private float[] mMonthOvertimeHours;
    private static final String TAG = "OvertimeSpan";


    public OvertimeSpan(float[] hours) {
        mMonthOvertimeHours = hours;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mHammerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHammerPaint.setStyle(Paint.Style.FILL);
        mHammerPaint.setColor(UIUtils.getColor(R.color.app_base_color));
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {
//        背景
        mPaint.setColor(UIUtils.getColor(R.color.background_gray));
        c.drawRect(left, top - UIUtils.getDimens(R.dimen.calendar_day_space) - 1, right - 1,
                right - top - UIUtils.getDimens(R.dimen.calendar_day_space), mPaint);


        float monthOvertimeHour = mMonthOvertimeHours[Integer.parseInt(text.toString()) - 1];
        if (monthOvertimeHour > 0) {
            //        画边框
            mPaint.setColor(0xffD81F26);
            c.drawRect(left, right - PixelUtil.dp2px(UIUtils.getContext(), UIUtils.getDimens(R.dimen.calendar_day_space)) -
                            getTextHeight(p), right - 1,
                    right - PixelUtil.dp2px(UIUtils.getContext(), UIUtils.getDimens(R.dimen.calendar_day_space)), mPaint);
            mPaint.setColor(0xffffffff);
            mPaint.setTextSize(p.getTextSize());
            c.drawText(monthOvertimeHour + " h", (left + right) / 2,
                    right - PixelUtil.dp2px(UIUtils.getContext(), UIUtils.getDimens(R.dimen.calendar_day_space)) - (getTextHeight(p) - baseline), mPaint);
        }
//      画锤子
        p.setColor(UIUtils.getColor(R.color.app_base_color));
        Path path = new Path();
        path.moveTo(right, top - UIUtils.getDimens(R.dimen.calendar_day_space) - 1);
        path.lineTo(right - 48, top - UIUtils.getDimens(R.dimen.calendar_day_space) - 1);
        path.lineTo(right, top - UIUtils.getDimens(R.dimen.calendar_day_space) - 1 + 36);
        c.drawPath(path, mHammerPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.hammer);

        c.drawBitmap(bitmap, right - 28, top - UIUtils.getDimens(R.dimen.calendar_day_space) + 2, mHammerPaint);
    }


    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent + 0.5);
    }
}
