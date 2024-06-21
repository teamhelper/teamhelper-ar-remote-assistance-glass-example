
package com.teamhelper.glass.view.activity;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.mst.basics.base.view.activity.GlassBaseActivity;
import com.mst.basics.slide.widget.FrameLayoutWidget;
import com.teamhelper.base.mvvm.databinding.view.adapter.BaseRecyclerViewAdapter;
import com.teamhelper.base.mvvm.databinding.viewmodel.EmptyViewModel;
import com.teamhelper.glass.R;
import com.teamhelper.glass.constants.Instruct;
import com.teamhelper.glass.databinding.ActivityMeetingHistoryBinding;
import com.teamhelper.glass.utils.ToastUtil;
import com.teamhelper.glass.view.adapter.MeetingHistoryAdapter;
import com.teamhelper.meeting.bean.meeting.MeetingBean;
import com.teamhelper.meeting.bean.meeting.MeetingHistoryBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;

import java.util.ArrayList;
import java.util.List;

public class MeetingHistoryActivity extends GlassBaseActivity<ActivityMeetingHistoryBinding, EmptyViewModel> {
    private final int PAGE_SIZE = 6;
    private int pageNum = 1;
    private int totalPage;
    private final List<MeetingBean> dataList = new ArrayList<>();
    private MeetingHistoryAdapter adapter;

    @Override
    public void initData() {

    }

    @Override
    public void initParams() {

    }

    @Override
    public void initView() {
        v.tvBack.setText(Instruct.BACK.getInstruct());
        v.tvBack.setOnClickListener(v -> finish());
        instructManager.addInstruct(Instruct.BACK, v.tvBack);
        v.tvTitle.setText(Instruct.HISTORY_MEETING.getInstruct());
        adapter = new MeetingHistoryAdapter(getMContext(), dataList);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<MeetingBean>() {
            @Override
            public void onItemClick(View view, int i, MeetingBean meetingBean) {
                super.onItemClick(view, i, meetingBean);
                MeetingManager.jumpToMessageActivity(getMContext(), meetingBean.getGroupId(), meetingBean.getMeetingName());
            }
        });
        v.recyclerView.setLayoutManager(new GridLayoutManager(getMContext(), 3));
        v.recyclerView.setAdapter(adapter);
        slideEventViewManager.setCheckViewListener(v.tvBack);
        v.instructMenu.setInstructManager(instructManager);
        v.instructMenu.addInstruct(Instruct.PREVIOUS_PAGE, v -> {
            if (pageNum <= 1) {
                ToastUtil.showToast(getMContext(), R.string.toast_first_page);
                return;
            }
            pageNum--;
            getMeetingHistory();
        });
        v.instructMenu.addInstruct(Instruct.NEXT_PAGE, v -> {
            if (pageNum >= totalPage) {
                ToastUtil.showToast(getMContext(), R.string.toast_last_page);
                return;
            }
            pageNum++;
            getMeetingHistory();
        });
    }

    @Override
    public void registerObserve() {

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
                adapter.refreshData();
                if (dataList.size() == 0) {
                    slideEventViewManager.setCheckViewListener(v.tvBack);
                    return;
                }
                v.recyclerView.onRenderComplete(unused -> {
                    FrameLayoutWidget widget = (FrameLayoutWidget) v.recyclerView.getLayoutManager().findViewByPosition(0);
                    slideEventViewManager.setCheckViewListener(widget);
                });
            }

            @Override
            public void onError(int code, String message) {
                ToastUtil.showToast(getMContext(), message);
            }
        });
    }

}
