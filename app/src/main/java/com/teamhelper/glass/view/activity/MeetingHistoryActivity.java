
package com.teamhelper.glass.view.activity;

import androidx.recyclerview.widget.GridLayoutManager;

import com.teamhelper.base.view.activity.RootActivity;
import com.teamhelper.base.view.widget.FrameLayoutWidget;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ActivityMeetingHistoryBinding;
import com.teamhelper.glass.enums.InstructSingle;
import com.teamhelper.glass.utils.ToastUtil;
import com.teamhelper.glass.view.adapter.MeetingHistoryAdapter;
import com.teamhelper.meeting.bean.meeting.MeetingBean;
import com.teamhelper.meeting.bean.meeting.MeetingHistoryBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;

import java.util.ArrayList;
import java.util.List;

public class MeetingHistoryActivity extends RootActivity<ActivityMeetingHistoryBinding> {
    private final int PAGE_SIZE = 6;
    private int pageNum = 1;
    private int totalPage;
    private final List<MeetingBean> dataList = new ArrayList<>();
    private MeetingHistoryAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_meeting_history;
    }

    @Override
    public void onCreate() {
        dataBinding.tvBack.setText(InstructSingle.BACK.getInstruct());
        dataBinding.tvBack.setOnClickListener(v -> finish());
        instructManager.addInstruct(InstructSingle.BACK, dataBinding.tvBack);
        dataBinding.tvTitle.setText(InstructSingle.HISTORY_MEETING.getInstruct());
        adapter = new MeetingHistoryAdapter(activity, dataList);
        adapter.setOnItemClickListener((v, position, itemData) -> {
            MeetingManager.jumpToMessageActivity(activity, itemData.getGroupId(), itemData.getMeetingName());
        });
        dataBinding.recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        dataBinding.recyclerView.setAdapter(adapter);
        slideEventViewManager.setCheckViewListener(dataBinding.tvBack);
        dataBinding.instructMenu.setInstructManager(instructManager);
        dataBinding.instructMenu.addInstruct(InstructSingle.PREVIOUS_PAGE, v -> {
            if (pageNum <= 1) {
                ToastUtil.showToast(activity, R.string.toast_first_page);
                return;
            }
            pageNum--;
            getMeetingHistory();
        });
        dataBinding.instructMenu.addInstruct(InstructSingle.NEXT_PAGE, v -> {
            if (pageNum >= totalPage) {
                ToastUtil.showToast(activity, R.string.toast_last_page);
                return;
            }
            pageNum++;
            getMeetingHistory();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        getMeetingHistory();
    }

    private void getMeetingHistory() {
        MeetingManager.getHistoryMeetings(pageNum, PAGE_SIZE, new IMeetingCallback<MeetingHistoryBean>() {
            @Override
            public void onSuccess(MeetingHistoryBean data) {
                totalPage = data.getTotal() % PAGE_SIZE == 0 ? data.getTotal() / PAGE_SIZE : data.getTotal() / PAGE_SIZE + 1;
                pageNum = data.getPageNum();
                dataList.clear();
                data.getData().forEach(item -> dataList.addAll(item.getMeetingList()));
                adapter.refresh();
                dataBinding.recyclerView.onRenderComplete(unused -> {
                    FrameLayoutWidget widget = (FrameLayoutWidget) dataBinding.recyclerView.getLayoutManager().findViewByPosition(0);
                    slideEventViewManager.setCheckViewListener(widget);
                });
            }

            @Override
            public void onError(int code, String message) {
                ToastUtil.showToast(activity, message);
            }
        });
    }
}
