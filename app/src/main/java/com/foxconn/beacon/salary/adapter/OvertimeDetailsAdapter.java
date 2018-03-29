package com.foxconn.beacon.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.foxconn.beacon.salary.view.StatisticsItemView;

import java.util.Date;
import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/11/8 0008.
 * @describe:
 */

public class OvertimeDetailsAdapter extends RecyclerView.Adapter {
    private List<DayWorkInfo> monthOvertimeInfo;
    private boolean mOvertimeInfo;
    private static final String TAG = "OvertimeDetailsAdapter";
    private boolean hasData;

    public OvertimeDetailsAdapter(List<DayWorkInfo> monthOvertimeInfo, boolean overtimeInfo) {
        this.monthOvertimeInfo = monthOvertimeInfo;
        this.mOvertimeInfo = overtimeInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_overtime_details, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DayWorkInfo dayWorkInfo = monthOvertimeInfo.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (mOvertimeInfo) {
            viewHolder.mStatisticsItemView.setRightText("+" + SalaryOperationHelper.getDayOvertimeSalary(UIUtils.getContext(),
                    dayWorkInfo.getOvertimeDuration(), dayWorkInfo.getOvertimeType(),
                    dayWorkInfo.getYear(), dayWorkInfo.getMonth()));
            viewHolder.mStatisticsItemView.setRightSubText(dayWorkInfo.getOvertimeDuration() + "小时/" +
                    dayWorkInfo.getOvertimeType() + "倍");
        } else {

            viewHolder.mStatisticsItemView.setRightSubText(dayWorkInfo.getLeaveDuration() + "小时/" +
                    DBOperatorHelper.getLeaveType(dayWorkInfo.getLeaveType()));
            viewHolder.mStatisticsItemView.setRightText("-" + SalaryOperationHelper.getDayOvertimeSalary(UIUtils.getContext(),
                    dayWorkInfo.getOvertimeDuration(), dayWorkInfo.getOvertimeType(),
                    dayWorkInfo.getYear(), dayWorkInfo.getMonth()));
            hasData = true;
        }
        viewHolder.mStatisticsItemView.setLeftName(DateUtils.formatTime(dayWorkInfo.getDatetime(), "MM.dd") +
                "(" + DateUtils.getDayOfWeek(new Date(dayWorkInfo.getDatetime()), false) + ")");
    }

    @Override
    public int getItemCount() {
        return monthOvertimeInfo == null ? 0 : monthOvertimeInfo.size();
    }
    /**
     * 返回数据状态
     *
     * @return
     */
    public boolean getDataStata() {
        return hasData;
    }

    public void refreshData(List<DayWorkInfo> monthOvertimeInfo, boolean bool) {
        this.monthOvertimeInfo = monthOvertimeInfo;
        mOvertimeInfo = bool;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        StatisticsItemView mStatisticsItemView;
        public ViewHolder(View itemView) {
            super(itemView);
            mStatisticsItemView = itemView.findViewById(R.id.statisticsItem_overtime_details);
        }
    }
}
