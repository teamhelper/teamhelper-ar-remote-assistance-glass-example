package com.teamhelper.glass.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.teamhelper.base.interfaces.ICornerMarkOnItemClickListener;
import com.teamhelper.base.interfaces.ISlideEventViewStateListener;
import com.teamhelper.base.view.widget.FrameLayoutWidget;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ItemMeetingHistoryBinding;
import com.teamhelper.glass.utils.DimensionUtil;
import com.teamhelper.glass.view.holder.BaseViewHolder;
import com.teamhelper.meeting.bean.meeting.MeetingBean;

import java.util.List;

public class MeetingHistoryAdapter extends BaseRecyclerAdapter<BaseViewHolder> {
    private final Context context;
    private final List<MeetingBean> dataList;
    private ICornerMarkOnItemClickListener<MeetingBean> onItemClickListener;

    public MeetingHistoryAdapter(Context context, List<MeetingBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(ICornerMarkOnItemClickListener<MeetingBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMeetingHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_meeting_history, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ItemMeetingHistoryBinding binding = (ItemMeetingHistoryBinding) holder.getDataBinding();
        MeetingBean item = dataList.get(position);
        binding.tvMeetingName.setText(item.getMeetingName());
        binding.tvTime.setText(item.getTimeStr());
        binding.tvInitiator.setText(context.getString(R.string.text_initiator) + item.getMasterName());
        binding.tvMeetingNo.setText(context.getString(R.string.text_meeting_no) + item.getMeetingNo());
        FrameLayoutWidget root = (FrameLayoutWidget) binding.getRoot();
        root.setContentDescription(String.valueOf(position + 1 + 1));
        root.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position, item, root.getInstructNumber());
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

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
