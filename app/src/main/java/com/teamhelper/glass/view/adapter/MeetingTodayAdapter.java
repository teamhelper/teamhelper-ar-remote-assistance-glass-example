package com.teamhelper.glass.view.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.mst.basics.databinding.EmptyBinding;
import com.mst.basics.slide.listener.ISlideEventViewStateListener;
import com.mst.basics.slide.widget.FrameLayoutWidget;
import com.teamhelper.base.mvvm.databinding.view.adapter.BaseRecyclerViewAdapter;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ItemMeetingTodayBinding;
import com.teamhelper.meeting.bean.meeting.MeetingBean;

import java.util.List;

public class MeetingTodayAdapter extends BaseRecyclerViewAdapter<MeetingBean, ItemMeetingTodayBinding, EmptyBinding> {

    public MeetingTodayAdapter(Context context, List<MeetingBean> dataList) {
        super(context, dataList);
    }

    @Override
    public int getBR() {
        return 0;
    }

    @Override
    public void onBindNonEmptyViewHolder(@NonNull ItemMeetingTodayBinding binding, MeetingBean meetingBean, int position) {
        binding.tvMeetingName.setText(meetingBean.getMeetingName());
        binding.tvTime.setText(meetingBean.getTimeStr());
        binding.tvMasterName.setText(context.getString(R.string.text_initiator) + meetingBean.getMasterName());
        binding.tvMeetingNo.setText(context.getString(R.string.text_meeting_no) + meetingBean.getMeetingNo());
        FrameLayoutWidget root = (FrameLayoutWidget) binding.getRoot();
        root.setContentDescription(String.valueOf(position + 1 + 2));
        root.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position, meetingBean);
            }
        });
        root.setSlideEventViewStateListener(new ISlideEventViewStateListener() {
            @Override
            public void onUncheck(View v) {
                super.onUncheck(v);
                binding.tvMeetingName.setTextColor(context.getColor(R.color.color_ffffff));
                binding.ivTime.setImageResource(R.mipmap.ic_meeting_time_uncheck);
                binding.tvTime.setTextColor(context.getColor(R.color.color_ffffff));
                binding.tvMasterName.setTextColor(context.getColor(R.color.color_ffffff));
                binding.tvMeetingNo.setTextColor(context.getColor(R.color.color_ffffff));
            }

            @Override
            public void onChecked(View v) {
                super.onChecked(v);
                binding.tvMeetingName.setTextColor(context.getColor(R.color.color_000000));
                binding.ivTime.setImageResource(R.mipmap.ic_meeting_time_checked);
                binding.tvTime.setTextColor(context.getColor(R.color.color_000000));
                binding.tvMasterName.setTextColor(context.getColor(R.color.color_000000));
                binding.tvMeetingNo.setTextColor(context.getColor(R.color.color_000000));
            }
        });
    }

}
