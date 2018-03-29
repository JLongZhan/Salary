package com.foxconn.beacon.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseApplication;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.foxconn.beacon.salary.view.StatisticsItemView;
import com.foxconn.beacon.salary.view.WheelView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe:
 */

public class RecordOvertimeAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private static final String TAG = "RecordOvertimeAdapter";
    /**
     * 工作日期
     */
    private static final int VIEW_TYPE_WORKDATE = 0;
    /**
     * 加班详情
     */
    private static final int VIEW_TYPE_OVERTIME_DETAIL = 1;
    /**
     * 请假详情
     */
    private static final int VIEW_TYPE_LEAVE_DETAIL = 2;
    /**
     * 备注
     */
    private static final int VIEW_TYPE_REMARK = 3;

    private WorkDateViewHolder mWorkDateViewHolder;
    private OverTimeDetailsViewHolder mOverTimeDetailsViewHolder;
    private LeaveDetailsViewHolder mLeaveDetailsViewHolder;
    private RemarkViewHolder mRemarkViewHolder;
    private DayWorkInfo mDayWorkInfo;
    private Date mDateInfo;

    public RecordOvertimeAdapter(DayWorkInfo dayWorkInfo, long selectDate) {
        mDateInfo = new Date(selectDate);
        mDayWorkInfo = dayWorkInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_WORKDATE:
                inflate = layoutInflater.inflate(R.layout.item_record_workdate, parent, false);
                return new WorkDateViewHolder(inflate);
            case VIEW_TYPE_OVERTIME_DETAIL:
                inflate = layoutInflater.inflate(R.layout.item_record_overtime_details, parent, false);
                return new OverTimeDetailsViewHolder(inflate);
            case VIEW_TYPE_LEAVE_DETAIL:
                inflate = layoutInflater.inflate(R.layout.item_record_leavetype, parent, false);
                return new LeaveDetailsViewHolder(inflate);
            case VIEW_TYPE_REMARK:
                inflate = layoutInflater.inflate(R.layout.item_record_remark, parent, false);
                return new RemarkViewHolder(inflate);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
//            工作
            case VIEW_TYPE_WORKDATE:
                mWorkDateViewHolder = (WorkDateViewHolder) holder;

                String stringBuilder = DateUtils.long2String(mDateInfo.getTime()) +
                        UIUtils.getString(R.string.space) + "|" + UIUtils.getString(R.string.space) +
                        DateUtils.getDayOfWeek(mDateInfo, true);
                mWorkDateViewHolder.mStatisticsItemWorkType.setLeftName(stringBuilder);

                mWorkDateViewHolder.mStatisticsItemWorkType.setRightText(
                        mDayWorkInfo == null ? "请选择出勤类型" : DBOperatorHelper.getWorkShiftType(mDayWorkInfo.getWorkType())
                );
                mWorkDateViewHolder.mStatisticsItemWorkDuration.setRightText(
                        mDayWorkInfo == null ? "8小时0分钟" :
                                getTimeHours(mDayWorkInfo.getWorkTime()) + getTimeMinutes(mDayWorkInfo.getWorkTime()));
//                工作时间和工作类型
                mWorkDateViewHolder.mStatisticsItemWorkDuration.setOnStatItemClickListener(this);
                mWorkDateViewHolder.mStatisticsItemWorkType.setOnStatItemClickListener(this);
                break;
//            加班
            case VIEW_TYPE_OVERTIME_DETAIL:
                StringBuilder overtimeType = new StringBuilder();
                mOverTimeDetailsViewHolder = (OverTimeDetailsViewHolder) holder;
//                小时分钟
                List<String> hours = Arrays.asList(BaseApplication.sContext.
                        getResources().getStringArray(R.array.work_hours));
                mOverTimeDetailsViewHolder.wheelViewHours.setItems(hours);
                List<String> minutes = Arrays.asList(BaseApplication.sContext.
                        getResources().getStringArray(R.array.work_minutes));
                mOverTimeDetailsViewHolder.wheelViewMinutes.setItems(minutes);

                mOverTimeDetailsViewHolder.statisticsItemOvertimeType.setRightText(
                        mDayWorkInfo == null ? overtimeType.append(SalaryOperationHelper.getWorkDateOvertimeSalary(UIUtils.getContext(),
                                DateUtils.getYear(mDateInfo),
                                DateUtils.getMonth(mDateInfo)))
                                .append("元/小时(工作日1.5倍)").toString() :
                                overtimeType.append(SalaryOperationHelper.getEachHourSalary(UIUtils.getContext(), mDayWorkInfo.getYear(), mDayWorkInfo.getMonth()))
                                        .append("元/小时")
                                        .append(DBOperatorHelper.getOvertimeType(mDayWorkInfo.getOvertimeType()))
                                        .toString());
//                设置选中
                mOverTimeDetailsViewHolder.wheelViewHours.setSeletion(mDayWorkInfo == null ? 0 :
                        hours.indexOf(getTimeHours(mDayWorkInfo.getOvertimeDuration())));
                mOverTimeDetailsViewHolder.wheelViewMinutes.setSeletion(mDayWorkInfo == null ? 0 :
                        hours.indexOf(getTimeMinutes(mDayWorkInfo.getOvertimeDuration())));
                //                加班类型的点击事件
                mOverTimeDetailsViewHolder.statisticsItemOvertimeType.setOnStatItemClickListener(this);
                break;
            /*
              请假时长
             */
            case VIEW_TYPE_LEAVE_DETAIL:
                mLeaveDetailsViewHolder = (LeaveDetailsViewHolder) holder;
//                设置请假类型
                mLeaveDetailsViewHolder.statisticsItemLeaveType.setRightText(UIUtils.getArrays(R.array.leave_type)
                        [mDayWorkInfo == null ? 0 : mDayWorkInfo.getLeaveType()]);
//                请假时长
                mLeaveDetailsViewHolder.statisticsItemLeaveDuration.setRightText(mDayWorkInfo == null ?
                        "0小时0分钟" :
                        getTimeHours(mDayWorkInfo.getLeaveDuration()) + getTimeMinutes(mDayWorkInfo.getLeaveDuration()));
                mLeaveDetailsViewHolder.statisticsItemLeaveDuration.setOnStatItemClickListener(this);
                mLeaveDetailsViewHolder.statisticsItemLeaveType.setOnStatItemClickListener(this);
                break;
            case VIEW_TYPE_REMARK:
                mRemarkViewHolder = (RemarkViewHolder) holder;
                mRemarkViewHolder.etRemark.setText(mDayWorkInfo == null ? "输入你的特别事件" : mDayWorkInfo.getRemark());
                break;
            default:
                break;
        }
    }

    /**
     * 将时间转换为小时分钟
     *
     * @param hours
     * @return
     */
    private String getTimeHours(float hours) {
        String s = String.valueOf(hours);
        if (s.contains(".")) {
            String[] split = s.split("\\.");
            return split[0] + "小时";
        } else {
            return "0小时";
        }
    }

    /**
     * 将时间转换为分钟
     *
     * @param hours
     * @return
     */
    private String getTimeMinutes(float hours) {
        String s = String.valueOf(hours);
        if (s.contains(".")) {
            String[] split = s.split("\\.");
            return split[1] + "分钟";
        } else {
            return "0分钟";
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return VIEW_TYPE_WORKDATE;
            case 1:
                return VIEW_TYPE_OVERTIME_DETAIL;
            case 2:
                return VIEW_TYPE_LEAVE_DETAIL;
            case 3:
                return VIEW_TYPE_REMARK;
            default:
                break;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener == null) {
            return;
        }
        mOnItemClickListener.onItemClick(v);
    }


    /**
     * 设置出勤班别
     *
     * @param text
     */
    public void setWorkType(String text) {
        mWorkDateViewHolder.mStatisticsItemWorkType.setRightText(text);
    }

    /**
     * 设置应出勤时间
     *
     * @param time
     */
    public void setWorkDuration(String time) {
        mWorkDateViewHolder.mStatisticsItemWorkDuration.setRightText(time);
    }

    /**
     * 获取应出勤时间
     *
     * @return 应出勤时间
     */
    public String getWorkDuration() {
        return mWorkDateViewHolder.mStatisticsItemWorkDuration.getRightText();
    }

    /**
     * 设置加班类型
     *
     * @param type
     */
    public void setOvertimeType(String type) {
        mOverTimeDetailsViewHolder.statisticsItemOvertimeType.setRightText(type);
    }

    /**
     * 获取加班时长
     *
     * @return 加班时长
     */
    public String getOvertimeDuration() {
        String sb = (TextUtils.isEmpty(mOverTimeDetailsViewHolder.wheelViewHours.getSeletedItem()) ? "0小时" : mOverTimeDetailsViewHolder.wheelViewHours.getSeletedItem()) +
                (TextUtils.isEmpty(mOverTimeDetailsViewHolder.wheelViewMinutes.getSeletedItem()) ? "0分钟" : mOverTimeDetailsViewHolder.wheelViewMinutes.getSeletedItem());
        return sb;
    }

    /**
     * 设置请假类型
     *
     * @param type
     */
    public void setLeaveType(String type) {
        mLeaveDetailsViewHolder.statisticsItemLeaveType.setRightText(type);
    }

    /**
     * 设置请假时间
     *
     * @param time
     */
    public void setLeaveDuration(String time) {
        mLeaveDetailsViewHolder.statisticsItemLeaveDuration.setRightText(time);
    }

    /**
     * 获取请假时间
     *
     * @return 请假时间
     */
    public String getLeaveDuration() {
        return mLeaveDetailsViewHolder.statisticsItemLeaveDuration.getRightText();
    }


    /**
     * 获取备注信息
     *
     * @return
     */
    public String getRemark() {
        if (mRemarkViewHolder != null) {
            return mRemarkViewHolder.etRemark.getText().toString() + "";
        }
        return "";
    }


    /**
     * 工作日期
     */
    class WorkDateViewHolder extends RecyclerView.ViewHolder {
        StatisticsItemView mStatisticsItemWorkType;
        StatisticsItemView mStatisticsItemWorkDuration;

        public WorkDateViewHolder(View itemView) {
            super(itemView);
            mStatisticsItemWorkType = itemView.findViewById(R.id.statisticsItem_work_type);
            mStatisticsItemWorkDuration = itemView.findViewById(R.id.statisticsItem_work_time);
        }
    }

    /**
     * 加班详情
     */
    static class OverTimeDetailsViewHolder extends RecyclerView.ViewHolder {
        WheelView wheelViewHours;
        WheelView wheelViewMinutes;
        StatisticsItemView statisticsItemOvertimeType;

        public OverTimeDetailsViewHolder(View itemView) {
            super(itemView);
            wheelViewHours = itemView.findViewById(R.id.wheel_record_hours);
            wheelViewMinutes = itemView.findViewById(R.id.wheel_record_minutes);
            statisticsItemOvertimeType = itemView.findViewById(R.id.statisticsItem_overtime_type);
        }
    }

    /**
     * 请假
     */
    static class LeaveDetailsViewHolder extends RecyclerView.ViewHolder {
        StatisticsItemView statisticsItemLeaveType;
        StatisticsItemView statisticsItemLeaveDuration;

        public LeaveDetailsViewHolder(View itemView) {
            super(itemView);
            statisticsItemLeaveType = itemView.findViewById(R.id.statisticsItem_leave_type);
            statisticsItemLeaveDuration = itemView.findViewById(R.id.statisticsItem_leave_duration);
        }
    }

    /**
     * 备注信息
     */
    static class RemarkViewHolder extends RecyclerView.ViewHolder {
        EditText etRemark;

        public RemarkViewHolder(View itemView) {
            super(itemView);
            etRemark = itemView.findViewById(R.id.et_record_remark);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }

    public void refresh(DayWorkInfo dayWorkInfo, Date date) {
        this.mDayWorkInfo = dayWorkInfo;
        this.mDateInfo =date;
        notifyDataSetChanged();
    }
}
