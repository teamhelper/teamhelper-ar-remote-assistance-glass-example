package com.teamhelper.glass.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mst.basics.databinding.EmptyBinding;
import com.mst.basics.slide.listener.ISlideEventViewStateListener;
import com.mst.basics.slide.widget.FrameLayoutWidget;
import com.teamhelper.base.mvvm.databinding.view.adapter.BaseRecyclerViewAdapter;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ItemMeetingHistoryBinding;
import com.teamhelper.glass.utils.DimensionUtil;
import com.teamhelper.meeting.bean.meeting.MeetingBean;

import java.util.List;

public class MeetingHistoryAdapter extends BaseRecyclerViewAdapter<MeetingBean, ItemMeetingHistoryBinding, EmptyBinding> {

    public MeetingHistoryAdapter(Context context, List<MeetingBean> dataList) {
        super(context, dataList);
    }

    @Override
    public int getBR() {
        return 0;
    }

    @Override
    public void onBindNonEmptyViewHolder(@NonNull ItemMeetingHistoryBinding binding, MeetingBean meetingBean, int position) {
        binding.tvMeetingName.setText(meetingBean.getMeetingName());
        binding.tvTime.setText(meetingBean.getTimeStr());
        binding.tvInitiator.setText(context.getString(R.string.text_initiator) + meetingBean.getMasterName());
        binding.tvMeetingNo.setText(context.getString(R.string.text_meeting_no) + meetingBean.getMeetingNo());
        FrameLayoutWidget root = (FrameLayoutWidget) binding.getRoot();
        root.setContentDescription(String.valueOf(position + 1 + 1));
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
                binding.icIcon.setImageResource(R.mipmap.ic_call_not_selected);
                binding.tvTime.setTextColor(context.getColor(R.color.color_ffffff));
                binding.tvInitiator.setTextColor(context.getColor(R.color.color_ffffff));
                binding.tvMeetingNo.setTextColor(context.getColor(R.color.color_ffffff));
            }

            @Override
            public void onChecked(View v) {
                super.onChecked(v);
                binding.tvMeetingName.setTextColor(context.getColor(R.color.color_000000));
                binding.icIcon.setImageResource(R.mipmap.ic_call_selected);
                binding.tvTime.setTextColor(context.getColor(R.color.color_000000));
                binding.tvInitiator.setTextColor(context.getColor(R.color.color_000000));
                binding.tvMeetingNo.setTextColor(context.getColor(R.color.color_000000));
            }
        });
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) root.getLayoutParams();
        if (position / 3 == 0) {
            layoutParams.bottomMargin = DimensionUtil.getDimensionPixelOffset(context, R.dimen.dp_10);
        } else {
            layoutParams.bottomMargin = DimensionUtil.getDimensionPixelOffset(context, R.dimen.dp_0);
        }
        root.setLayoutParams(layoutParams);
    }

}
