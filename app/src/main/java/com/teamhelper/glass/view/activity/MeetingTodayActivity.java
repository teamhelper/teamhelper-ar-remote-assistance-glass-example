package com.teamhelper.glass.view.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mst.basics.base.view.activity.GlassBaseActivity;
import com.mst.basics.slide.widget.FrameLayoutWidget;
import com.teamhelper.base.mvvm.databinding.view.adapter.BaseRecyclerViewAdapter;
import com.teamhelper.base.mvvm.databinding.viewmodel.EmptyViewModel;
import com.teamhelper.glass.R;
import com.teamhelper.glass.constants.Instruct;
import com.teamhelper.glass.databinding.ActivityMeetingTodayBinding;
import com.teamhelper.glass.manager.IntentManager;
import com.teamhelper.glass.utils.ToastUtil;
import com.teamhelper.glass.view.adapter.MeetingTodayAdapter;
import com.teamhelper.meeting.bean.meeting.MeetingBean;
import com.teamhelper.meeting.bean.meeting.MeetingDetailBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.tools.ActivityStackManager;

import java.util.ArrayList;
import java.util.List;

public class MeetingTodayActivity extends GlassBaseActivity<ActivityMeetingTodayBinding, EmptyViewModel> {
    private static final int REQUEST_CODE = 1000;
    private final List<MeetingBean> dataList = new ArrayList<>();
    private MeetingTodayAdapter adapter;

    @Override
    public void initData() {

    }

    @Override
    public void initParams() {

    }

    @Override
    public void initView() {
        v.tvBack.setText(Instruct.BACK.getInstruct());
        v.tvBack.setOnClickListener(v -> {
            MeetingManager.logout(null);
            ActivityStackManager.getInstance().finishActivityList();
            IntentManager.build(LoginActivity.class).startActivity(getMContext());
        });
        instructManager.addInstruct(Instruct.BACK, v.tvBack);
        v.tvTitle.setText(R.string.text_meeting_today_list);
        v.tvHistoryMeeting.setText(Instruct.HISTORY_MEETING.getInstruct());
        v.tvHistoryMeeting.setOnClickListener(v -> {
            IntentManager.build(MeetingHistoryActivity.class).startActivity(getMContext());
        });
        instructManager.addInstruct(Instruct.HISTORY_MEETING, v.tvHistoryMeeting);
        slideEventViewManager.setCheckViewListener(v.tvBack);
        adapter = new MeetingTodayAdapter(getMContext(), dataList);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<MeetingBean>() {
            @Override
            public void onItemClick(View view, int i, MeetingBean meetingBean) {
                super.onItemClick(view, i, meetingBean);
                joinMeeting(meetingBean.getMeetingNo());
            }
        });
        v.recyclerView.setLayoutManager(new LinearLayoutManager(getMContext(), LinearLayoutManager.HORIZONTAL, false));
        v.recyclerView.setAdapter(adapter);

        v.instructMenu.setInstructManager(instructManager);
        v.instructMenu.addInstruct(Instruct.LAUNCHER_MEETING, v -> {
            IntentManager.build(MeetingMemberActivity.class).startActivityForResult(getMContext(), REQUEST_CODE);
        });
        showLoading("Loading...");
    }

    @Override
    public void registerObserve() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getMeetings();
    }

    private void getMeetings() {
        MeetingManager.getMeetings(new IMeetingCallback<List<MeetingBean>>() {
            @Override
            public void onSuccess(List<MeetingBean> data) {
                dismissLoading();
                dataList.clear();
                dataList.addAll(data);
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
                dismissLoading();
                ToastUtil.showToast(getMContext(), message);
            }
        });
    }

    private void joinMeeting(String meetingNo) {
        showLoading("Loading...");
        MeetingManager.joinMeeting(getMContext(), meetingNo, new IMeetingCallback<MeetingDetailBean>() {
            @Override
            public void onSuccess(MeetingDetailBean data) {
                dismissLoading();
            }

            @Override
            public void onError(int code, String message) {
                dismissLoading();
                ToastUtil.showToast(getMContext(), message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE) {
            ToastUtil.showToast(getMContext(), "正在发起协作");
            ArrayList<String> inviteeUserIds = data.getStringArrayListExtra("inviteeUserIds");
            String meetingName = MeetingManager.getUserBean().getNickname() + "的快速协作";
            MeetingManager.createJoinMeeting(getMContext(), meetingName, inviteeUserIds, new IMeetingCallback<MeetingDetailBean>() {
                @Override
                public void onSuccess(MeetingDetailBean data) {
                }

                @Override
                public void onError(int code, String message) {
                    ToastUtil.showToast(getMContext(), message);
                }
            });
        }
    }

    @Override
    public void onBack() {
        super.onBack();
        ActivityStackManager.getInstance().finishAllActivity();
    }
}
