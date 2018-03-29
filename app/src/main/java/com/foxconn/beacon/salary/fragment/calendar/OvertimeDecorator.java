package com.foxconn.beacon.salary.fragment.calendar;

import android.util.Log;

import com.foxconn.beacon.salary.model.OverTimeBean;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */
public class OvertimeDecorator implements DayViewDecorator {
    private static final String TAG = "OvertimeDecorator";
    private String mHours;
    private List<OverTimeBean> mOverTimeBeen;

    public OvertimeDecorator() {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Log.i(TAG, "shouldDecorate: ");
        mOverTimeBeen = DataSupport.where("dateTime=?",
                String.valueOf(day.getDate().getTime())).find(OverTimeBean.class);
        return mOverTimeBeen.size() > 0;
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (mOverTimeBeen != null&&mOverTimeBeen.size() > 0){
            mHours = String.valueOf(mOverTimeBeen.get(0).getOvertimeHours());
        }

        view.addSpan(new OvertimeSpan());
    }
}
