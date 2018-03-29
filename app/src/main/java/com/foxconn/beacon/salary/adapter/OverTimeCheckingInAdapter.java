package com.foxconn.beacon.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class OverTimeCheckingInAdapter extends RecyclerView.Adapter {
    List<DayWorkInfo> mDayOvertimes;
    DecimalFormat mDecimalFormat;

    public OverTimeCheckingInAdapter(List<DayWorkInfo> overtimeList) {
        mDayOvertimes = overtimeList;
        mDecimalFormat = new DecimalFormat("0.00");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checking_in_record, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DayWorkInfo dayWorkInfo = mDayOvertimes.get(position);
        viewHolder.mNearestDate.setText(DateUtils.date2String(new Date(dayWorkInfo.getDatetime()), "MM.dd"));
        viewHolder.mNearestDay.setText(DateUtils.getDayOfWeek(new Date(dayWorkInfo.getDatetime()),false));
        viewHolder.mNearestWorkTime.setText(mDecimalFormat.format(dayWorkInfo.getWorkTime()));
        viewHolder.mNearestOvertimeDuration.setText(mDecimalFormat.format(dayWorkInfo.getOvertimeDuration()));
        viewHolder.mNearestOvertimeType.setText(mDecimalFormat.format(dayWorkInfo.getOvertimeType()));
        viewHolder.mNearestOvertimeIncome.setText(mDecimalFormat.format(
                SalaryOperationHelper.getDayOvertimeSalary(
                        UIUtils.getContext(),
                        dayWorkInfo.getOvertimeDuration(),
                        dayWorkInfo.getOvertimeType(),
                        dayWorkInfo.getYear(),
                        dayWorkInfo.getMonth()
                )));

    }

    @Override
    public int getItemCount() {
        return mDayOvertimes == null ? 0 : mDayOvertimes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNearestDate;
        TextView mNearestDay;
        TextView mNearestWorkTime;
        TextView mNearestOvertimeDuration;
        TextView mNearestOvertimeIncome;
        TextView mNearestOvertimeType;

        public ViewHolder(View itemView) {
            super(itemView);
            mNearestDate = itemView.findViewById(R.id.tv_overtime_nearest_date);
            mNearestDay = itemView.findViewById(R.id.tv_overtime_nearest_day);
            mNearestWorkTime = itemView.findViewById(R.id.tv_overtime_nearest_work_time);
            mNearestOvertimeDuration = itemView.findViewById(R.id.tv_overtime_nearest_duration);
            mNearestOvertimeIncome = itemView.findViewById(R.id.tv_overtime_nearest_income);
            mNearestOvertimeType = itemView.findViewById(R.id.tv_overtime_nearest_type);
        }
    }
}
