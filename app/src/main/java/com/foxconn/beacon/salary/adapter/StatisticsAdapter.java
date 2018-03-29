package com.foxconn.beacon.salary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.listener.OnAdapterItemClickListener;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.MonthSetting;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.PixelUtil;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.foxconn.beacon.salary.view.StatisticsItemView;
import com.foxconn.beacon.salary.view.StatisticsTitleView;

import java.text.DecimalFormat;

/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe:
 */

public class StatisticsAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private static final String TAG = "StatisticsAdapter";
    private static final int VIEW_TYPE_ALL_INCOMING = 0;
    private static final int VIEW_TYPE_BASIC_PROJECT = 1;
    private static final int VIEW_TYPE_SUBSIDY_PROJECT = 2;
    private static final int VIEW_TYPE_DEDUCT_PROJECT = 3;
    private static final int VIEW_TYPE_AGENCY_PROJECT = 4;
    private int mCurrentYear;
    private int mCurrentMonth;
    private DecimalFormat mDecimalFormat;
    private SubsidyProjectViewHolder mSubsidyProjectViewHolder;
    private DeductProjectViewHolder mDeductProjectViewHolder;
    private MonthSetting mMonthSetting;

    public StatisticsAdapter(int currentYear, int currentMonth) {
        mCurrentYear = currentYear;
        mCurrentMonth = currentMonth;
        mDecimalFormat = new DecimalFormat("0.00");
        mMonthSetting = DBOperatorHelper.getMonthSetting(mCurrentYear, mCurrentMonth);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate;
        switch (viewType) {
//            总收入
            case VIEW_TYPE_ALL_INCOMING:
                inflate = layoutInflater.inflate(R.layout.item_statistics_all_incoming, parent, false);
                return new AllIncomeViewHolder(inflate);
//            基本项目
            case VIEW_TYPE_BASIC_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_basic_project, parent, false);
                return new BasicProjectViewHolder(inflate);
//            补贴项目
            case VIEW_TYPE_SUBSIDY_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_subsidy, parent, false);
                return new SubsidyProjectViewHolder(inflate);
//              扣费项目
            case VIEW_TYPE_DEDUCT_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_deduct_project, parent, false);
                return new DeductProjectViewHolder(inflate);
//              代缴费项目
            case VIEW_TYPE_AGENCY_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_agency_project, parent, false);
                return new AgencyProjectViewHolder(inflate);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context ctx = UIUtils.getContext();
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ALL_INCOMING:
                AllIncomeViewHolder allIncomeViewHolder = (AllIncomeViewHolder) holder;
                allIncomeViewHolder.tvAllIncome.setText(SalaryOperationHelper.getFSalary(ctx, mCurrentYear, mCurrentMonth) + "");
                break;
//            基本项目
            case VIEW_TYPE_BASIC_PROJECT:
                BasicProjectViewHolder basicProjectViewHolder = (BasicProjectViewHolder) holder;
//                设置基本工资
                basicProjectViewHolder.statisticsItemBasicSalary.setRightText(SalaryOperationHelper.getBasicSalary(ctx, mCurrentYear, mCurrentMonth) + "元");
//                加班收入
                basicProjectViewHolder.statisticsItemOvertimeIncome.setRightText(SalaryOperationHelper.getMonthOvertimeIncome(ctx, mCurrentYear, mCurrentMonth) + "元");
//                调休
                basicProjectViewHolder.statisticsItemAdjustment.setLeftName("调休");
                basicProjectViewHolder.statisticsItemAdjustment.setLeftSubText(SalaryOperationHelper.getAdjustHourCost(ctx, mCurrentYear, mCurrentMonth) + "元/小时");
                basicProjectViewHolder.statisticsItemAdjustment.setRightText(SalaryOperationHelper.getAdjustCost(ctx, mCurrentYear, mCurrentMonth) + "元");
                basicProjectViewHolder.statisticsItemAdjustment.setRightSubText(DBOperatorHelper.getAdjustHours(mCurrentYear, mCurrentMonth) + "小时");
//                总收入
                String allIncome = mDecimalFormat.format((SalaryOperationHelper.getMonthOvertimeIncome(
                        ctx,
                        mCurrentYear, mCurrentMonth) + SalaryOperationHelper.getBasicSalary(
                        ctx, mCurrentYear, mCurrentMonth)));
                basicProjectViewHolder.statisticsTitleBasicProject.setTitleRightName("共：" + allIncome + "元");

                basicProjectViewHolder.statisticsItemBasicSalary.setOnStatItemClickListener(this);
                basicProjectViewHolder.statisticsItemBasicSalary.setTag(VIEW_TYPE_BASIC_PROJECT);
                basicProjectViewHolder.statisticsItemOvertimeIncome.setOnStatItemClickListener(this);
                basicProjectViewHolder.statisticsItemOvertimeIncome.setTag(VIEW_TYPE_BASIC_PROJECT);
                basicProjectViewHolder.statisticsItemAdjustment.setOnStatItemClickListener(this);
                basicProjectViewHolder.statisticsItemAdjustment.setTag(VIEW_TYPE_BASIC_PROJECT);
                break;
//            补贴项目
            case VIEW_TYPE_SUBSIDY_PROJECT:
                mSubsidyProjectViewHolder = (SubsidyProjectViewHolder) holder;
                mSubsidyProjectViewHolder.statisticsTitleSubsidyProject.setTitleRightName("共：" +
                        SalaryOperationHelper.getOtherIncome(mCurrentYear, mCurrentMonth) + "元");
                mSubsidyProjectViewHolder.llSubsidyProject.removeAllViews();
                addProjectItem(mMonthSetting.getSubsidyItemChecked(), false);
                mSubsidyProjectViewHolder.btnAddSubsidyProject.setOnClickListener(this);
                mSubsidyProjectViewHolder.btnAddSubsidyProject.setTag(VIEW_TYPE_SUBSIDY_PROJECT);
                break;
//            扣款项目
            case VIEW_TYPE_DEDUCT_PROJECT:
                mDeductProjectViewHolder = (DeductProjectViewHolder) holder;
                mDeductProjectViewHolder.llDeductProject.removeAllViews();
                mDeductProjectViewHolder.statisticsTitleDeductProject.setTitleRightName("共：-" +
                        SalaryOperationHelper.getOtherExpenditure(UIUtils.getContext(), mCurrentYear, mCurrentMonth) + "元");
                addProjectItem(mMonthSetting.getDeductItemChecked(), true);
                mDeductProjectViewHolder.btnAddDeductProject.setOnClickListener(this);
                mDeductProjectViewHolder.btnAddDeductProject.setTag(VIEW_TYPE_DEDUCT_PROJECT);
                break;
//            代缴费项目
            case VIEW_TYPE_AGENCY_PROJECT:
                AgencyProjectViewHolder agencyProjectViewHolder = (AgencyProjectViewHolder) holder;
//                个人所得税
                agencyProjectViewHolder.statisticsITemIncomeTax.setRightText(SalaryOperationHelper.getIncomeTax(ctx,
                        mCurrentYear, mCurrentMonth) + "元");
//                设置社保
                agencyProjectViewHolder.statisticsItemSocialSecurity.setRightText(SalaryOperationHelper.getSocialSecurity(
                        ctx, mCurrentYear, mCurrentMonth
                ) + "元");
//                设置公积金
                agencyProjectViewHolder.statisticsItemAccumulationFund.setRightText(SalaryOperationHelper.getAccumulationFund(
                        ctx, mCurrentYear, mCurrentMonth
                ) + "元");
                agencyProjectViewHolder.statisticsTitleAgencyProject.setTitleRightName("共：-" +
                        SalaryOperationHelper.getMonthAgencyDeduct(ctx, mCurrentYear, mCurrentMonth) + "元"
                );
                agencyProjectViewHolder.statisticsItemSocialSecurity.setOnStatItemClickListener(this);
                agencyProjectViewHolder.statisticsItemSocialSecurity.setTag(VIEW_TYPE_AGENCY_PROJECT);
                agencyProjectViewHolder.statisticsItemAccumulationFund.setOnStatItemClickListener(this);
                agencyProjectViewHolder.statisticsItemAccumulationFund.setTag(VIEW_TYPE_AGENCY_PROJECT);
                agencyProjectViewHolder.statisticsITemIncomeTax.setOnStatItemClickListener(this);
                agencyProjectViewHolder.statisticsITemIncomeTax.setTag(VIEW_TYPE_AGENCY_PROJECT);
                break;
            default:
                break;
        }
    }

    /**
     * 根据索引来判断返回哪一个扣款收入
     *
     * @param i
     * @return
     */
    private StatisticsItemView getDeductProjectItem(int i) {
        String[] arrays = UIUtils.getArrays(R.array.deduct_project);
        StatisticsItemView statisticsItemView = new StatisticsItemView(UIUtils.getContext());
        statisticsItemView.setLeftName(arrays[i]);
        float deduct = .0f;
        switch (i) {
//            事假
            case 0:
//                设置条目相关信息
                statisticsItemView.setId(R.id.item_things_leave);
                deduct = SalaryOperationHelper.getThingLeaveHour(UIUtils.getContext(), mCurrentYear, mCurrentMonth);
                statisticsItemView.setLeftName(arrays[i]);
                statisticsItemView.setLeftSubText(deduct + "元/小时");
                float thingsLeaveHours = DBOperatorHelper.getMonthThingsLeaveHours(mCurrentYear, mCurrentMonth);
                statisticsItemView.setRightText(thingsLeaveHours * deduct + "元");
                statisticsItemView.setRightSubText(thingsLeaveHours + "小时");
                return statisticsItemView;
//            病假
            case 1:
                statisticsItemView.setId(R.id.item_sick_leave);
                deduct = SalaryOperationHelper.getSickLeaveHour(UIUtils.getContext(), mCurrentYear, mCurrentMonth);
                statisticsItemView.setLeftName(arrays[i]);
                statisticsItemView.setLeftSubText(deduct + "元/小时");
                float sickLeaveHours = DBOperatorHelper.getMonthSickLeaveHours(mCurrentYear, mCurrentMonth);
                statisticsItemView.setRightText(SalaryOperationHelper.getSickLeaveCost(UIUtils.getContext(), mCurrentYear, mCurrentMonth) + "元");
                statisticsItemView.setRightSubText(sickLeaveHours + "小时");
                return statisticsItemView;
//            食堂消费
            case 2:
                statisticsItemView.setId(R.id.item_mess_consume);
                deduct = mMonthSetting.getMessConsume();
                break;
//            水电费
            case 3:
                statisticsItemView.setId(R.id.item_utilities);
                deduct = mMonthSetting.getUtilities();
                break;
//            住宿费
            case 4:
                deduct = mMonthSetting.getQuarterage();
                statisticsItemView.setId(R.id.item_quarterage);
                break;
//            其他扣
            case 5:
                statisticsItemView.setId(R.id.item_other_deduct);
                deduct = mMonthSetting.getOtherDeduct();
                break;
            default:
                break;
        }
        statisticsItemView.setRightText(deduct + "元");
        return statisticsItemView;
    }

    /**
     * 根据索引来判断返回哪一个补贴收入
     *
     * @param i
     * @return
     */
    private StatisticsItemView getSubsidyIncome(int i) {
        String[] arrays = UIUtils.getArrays(R.array.income_project);
        StatisticsItemView statisticsItemView = new StatisticsItemView(UIUtils.getContext());
        statisticsItemView.setLeftName(arrays[i]);
        float income = 0.0f;
        switch (i) {
            case 0:
                statisticsItemView.setId(R.id.item_day_shift_subsidy);
                income = mMonthSetting.getDayShiftSubsidy();
                statisticsItemView.setLeftName(arrays[i]);
                statisticsItemView.setLeftSubText(income + "元/天");
                int monthWorkTypeDays = DBOperatorHelper.getMonthWorkTypeDays(mCurrentYear, mCurrentMonth, 0) +
                        DBOperatorHelper.getMonthWorkTypeDays(mCurrentYear, mCurrentMonth, 1);
                statisticsItemView.setRightText(monthWorkTypeDays * income + "元");
                statisticsItemView.setRightSubText(monthWorkTypeDays + "天");
                return statisticsItemView;
//            中班
            case 1:
                statisticsItemView.setId(R.id.item_middle_shift_subsidy);
                income = mMonthSetting.getMiddleShiftSubsidy();
                statisticsItemView.setLeftName(arrays[i]);
                statisticsItemView.setLeftSubText(income + "元/天");
                int monthWorkTypeDays1 = DBOperatorHelper.getMonthWorkTypeDays(mCurrentYear, mCurrentMonth, 2);
                statisticsItemView.setRightText(monthWorkTypeDays1 * income + "元");
                statisticsItemView.setRightSubText(monthWorkTypeDays1 + "天");
                return statisticsItemView;
            case 2:
                statisticsItemView.setId(R.id.item_night_shift_subsidy);
                income = mMonthSetting.getNightShiftSubsidy();
                int monthWorkTypeDays2 = DBOperatorHelper.getMonthWorkTypeDays(mCurrentYear, mCurrentMonth, 3);
                statisticsItemView.setRightText(monthWorkTypeDays2 * income + "元");
                statisticsItemView.setRightSubText(monthWorkTypeDays2 + "天");
                statisticsItemView.setLeftName(arrays[i]);
                statisticsItemView.setLeftSubText(income + "元/天");
                return statisticsItemView;
            case 3:
                statisticsItemView.setId(R.id.item_attendance_bonus_subsidy);
                income = mMonthSetting.getAttendanceBonus();
                break;
            case 4:
                statisticsItemView.setId(R.id.item_traffic_subsidy);
                income = mMonthSetting.getTrafficSubsidy();
                break;
            case 5:
                statisticsItemView.setId(R.id.item_food_subsidy);
                income = mMonthSetting.getFoodSubsidy();
                break;
            case 6:
                statisticsItemView.setId(R.id.item_live_subsidy);
                income = mMonthSetting.getLiveSubsidy();
                break;
            case 7:
                statisticsItemView.setId(R.id.item_job_subsidy);
                income = mMonthSetting.getJobSubsidy();
                break;
            case 8:
                statisticsItemView.setId(R.id.item_hyperthermia_subsidy);
                income = mMonthSetting.getHyperthermiaSubsidy();
                break;
            case 9:
                statisticsItemView.setId(R.id.item_environment_subsidy);
                income = mMonthSetting.getEnvironmentSubsidy();
                break;
            case 10:
                statisticsItemView.setId(R.id.item_performance_subsidy);
                income = mMonthSetting.getPerformanceSubsidy();
                break;
            case 11:
                statisticsItemView.setId(R.id.item_other_subsidy);
                income = mMonthSetting.getOtherSubsidy();
                break;
            default:
                break;
        }
        statisticsItemView.setRightText(income + "元");

        return statisticsItemView;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
//                总收入
                return VIEW_TYPE_ALL_INCOMING;
            case 1:
//                基本项目
                return VIEW_TYPE_BASIC_PROJECT;
            case 2:
//                补贴项目
                return VIEW_TYPE_SUBSIDY_PROJECT;
            case 3:
//                扣款项目
                return VIEW_TYPE_DEDUCT_PROJECT;
            case 4:
//                代缴费项目
                return VIEW_TYPE_AGENCY_PROJECT;
            default:
                return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener == null) {
            return;
        }
        mOnItemClickListener.onItemClick(v, (int) v.getTag());
    }

    /**
     * 添加扣款项目
     */
    public void addProjectItem(String name, boolean isDeduct) {
        if (!TextUtils.isEmpty(name)) {
            String[] split = name.split("@");
            for (int i = 0; i < split.length; i++) {
                if (Boolean.parseBoolean(split[i])) {
                    if (isDeduct) {
                        StatisticsItemView deductProjectItem = getDeductProjectItem(i);
                        deductProjectItem.setOnStatItemClickListener(this);
                        deductProjectItem.setTag(VIEW_TYPE_DEDUCT_PROJECT);
                        mDeductProjectViewHolder.llDeductProject.addView(deductProjectItem);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) deductProjectItem.getLayoutParams();
                        layoutParams.topMargin = PixelUtil.dp2px(UIUtils.getContext(), 1);
                        deductProjectItem.setLayoutParams(layoutParams);
                    } else {
                        StatisticsItemView subsidyIncome = getSubsidyIncome(i);
                        subsidyIncome.setOnStatItemClickListener(this);
                        subsidyIncome.setTag(VIEW_TYPE_SUBSIDY_PROJECT);
                        mSubsidyProjectViewHolder.llSubsidyProject.addView(subsidyIncome);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) subsidyIncome.getLayoutParams();
                        layoutParams.topMargin = PixelUtil.dp2px(UIUtils.getContext(), 1);
                        subsidyIncome.setLayoutParams(layoutParams);
                    }
                }
            }
        }
//            判断隐藏
        if (isDeduct) {
            if (mDeductProjectViewHolder.llDeductProject.getChildCount() > 0) {
                mDeductProjectViewHolder.tvDeductDescription.setVisibility(View.GONE);
            } else {
                mDeductProjectViewHolder.llDeductProject.removeAllViews();
                mDeductProjectViewHolder.tvDeductDescription.setVisibility(View.VISIBLE);
            }
        } else {
            if (mSubsidyProjectViewHolder.llSubsidyProject.getChildCount() > 0) {
                mSubsidyProjectViewHolder.tvSubsidyDescription.setVisibility(View.GONE);
            } else {
                mSubsidyProjectViewHolder.llSubsidyProject.removeAllViews();
                mSubsidyProjectViewHolder.tvSubsidyDescription.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 刷新数据
     *
     * @param year
     * @param month
     */
    public void refreshDate(int year, int month) {
        this.mCurrentYear = year;
        this.mCurrentMonth = month;
        notifyDataSetChanged();
    }

    /**
     * 总收入
     */
    static class AllIncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvAllIncome;

        public AllIncomeViewHolder(View itemView) {
            super(itemView);
            tvAllIncome = itemView.findViewById(R.id.tv_statistics_all_incoming);
        }
    }

    /**
     * 基本项目
     */
    static class BasicProjectViewHolder extends RecyclerView.ViewHolder {
        StatisticsTitleView statisticsTitleBasicProject;
        StatisticsItemView statisticsItemBasicSalary;
        StatisticsItemView statisticsItemOvertimeIncome;
        StatisticsItemView statisticsItemAdjustment;

        public BasicProjectViewHolder(View itemView) {
            super(itemView);
            statisticsTitleBasicProject = itemView.findViewById(R.id.statisticsTitle_basic_project);
            statisticsItemBasicSalary = itemView.findViewById(R.id.statisticsItem_basic_salary);
            statisticsItemOvertimeIncome = itemView.findViewById(R.id.statisticsItem_overtime_income);
            statisticsItemAdjustment = itemView.findViewById(R.id.statisticsItem_adjustment);
        }
    }

    /**
     * 补贴项目
     */
    static class SubsidyProjectViewHolder extends RecyclerView.ViewHolder {
        StatisticsTitleView statisticsTitleSubsidyProject;
        LinearLayout llSubsidyProject;
        TextView tvSubsidyDescription;
        Button btnAddSubsidyProject;

        public SubsidyProjectViewHolder(View itemView) {
            super(itemView);
            llSubsidyProject = itemView.findViewById(R.id.ll_subsidy_project);
            statisticsTitleSubsidyProject = itemView.findViewById(R.id.statisticsTitle_subsidy);
            tvSubsidyDescription = itemView.findViewById(R.id.tv_statistics_subsidy_description);
            btnAddSubsidyProject = itemView.findViewById(R.id.btn_statistics_add_subsidy);
        }
    }

    /**
     * 扣款项目
     */
    static class DeductProjectViewHolder extends RecyclerView.ViewHolder {
        StatisticsTitleView statisticsTitleDeductProject;
        LinearLayout llDeductProject;
        TextView tvDeductDescription;
        Button btnAddDeductProject;

        public DeductProjectViewHolder(View itemView) {
            super(itemView);
            statisticsTitleDeductProject = itemView.findViewById(R.id.statisticsTitle_deduct_project);
            llDeductProject = itemView.findViewById(R.id.ll_deduct_project);
            tvDeductDescription = itemView.findViewById(R.id.tv_statistics_deduct_description);
            btnAddDeductProject = itemView.findViewById(R.id.btn_statistics_add_deduct);
        }
    }

    private OnAdapterItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnAdapterItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 代缴费项目
     */
    static class AgencyProjectViewHolder extends RecyclerView.ViewHolder {
        StatisticsTitleView statisticsTitleAgencyProject;
        //        社保
        StatisticsItemView statisticsItemSocialSecurity;
        //       公积金
        StatisticsItemView statisticsItemAccumulationFund;
        //        个人所得税
        StatisticsItemView statisticsITemIncomeTax;

        public AgencyProjectViewHolder(View itemView) {
            super(itemView);
            statisticsTitleAgencyProject = itemView.findViewById(R.id.statisticsTitle_agency_project);
            statisticsItemSocialSecurity = itemView.findViewById(R.id.statisticsItem_social_security);
            statisticsItemAccumulationFund = itemView.findViewById(R.id.statisticsItem_accumulation_fund);
            statisticsITemIncomeTax = itemView.findViewById(R.id.statisticsItem_income_tax);
        }
    }
}
