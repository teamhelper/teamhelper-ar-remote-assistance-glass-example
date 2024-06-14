package com.teamhelper.glass.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.teamhelper.base.view.activity.RootActivity;
import com.teamhelper.base.view.widget.FrameLayoutWidget;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ActivityMeetingTodayBinding;
import com.teamhelper.glass.enums.InstructSingle;
import com.teamhelper.glass.manager.IntentManager;
import com.teamhelper.glass.utils.ToastUtil;
import com.teamhelper.glass.view.adapter.MeetingTodayAdapter;
import com.teamhelper.meeting.bean.meeting.MeetingBean;
import com.teamhelper.meeting.bean.meeting.MeetingDetailBean;
import com.teamhelper.meeting.enums.InstructNumber;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.tools.ActivityStackManager;

import java.util.ArrayList;
import java.util.List;

public class MeetingTodayActivity extends RootActivity<ActivityMeetingTodayBinding> {
    private static final int REQUEST_CODE = 1000;
    private final List<MeetingBean> dataList = new ArrayList<>();
    private MeetingTodayAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_meeting_today;
    }

    @Override
    public void onCreate() {
        dataBinding.tvBack.setText(InstructSingle.BACK.getInstruct());
        dataBinding.tvBack.setOnClickListener(v -> {
            MeetingManager.logout(null);
            ActivityStackManager.getInstance().finishActivityList();
            IntentManager.build(LoginActivity.class).startActivity(activity);
        });
        instructManager.addInstruct(InstructSingle.BACK, dataBinding.tvBack);
        dataBinding.tvTitle.setText(R.string.text_meeting_today_list);
        dataBinding.tvHistoryMeeting.setText(InstructSingle.HISTORY_MEETING.getInstruct());
        dataBinding.tvHistoryMeeting.setOnClickListener(v -> {
            IntentManager.build(MeetingHistoryActivity.class).startActivity(activity);
        });
        instructManager.addInstruct(InstructSingle.HISTORY_MEETING, dataBinding.tvHistoryMeeting);
        slideEventViewManager.addInstructNumber(InstructNumber.SELECT);
        slideEventViewManager.setCheckViewListener(dataBinding.tvBack);
        adapter = new MeetingTodayAdapter(activity, dataList);
        adapter.setOnItemClickListener((v, position, itemData, instructNumber) -> joinMeeting(itemData.getMeetingNo()));
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        dataBinding.recyclerView.setAdapter(adapter);

        dataBinding.instructMenu.setInstructManager(instructManager);
        dataBinding.instructMenu.addInstruct(InstructSingle.LAUNCHER_MEETING, v -> {
            IntentManager.build(MeetingMemberActivity.class).startActivityForResult(activity, REQUEST_CODE);
        });
        showLoading();
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
                adapter.refresh();
                dataBinding.recyclerView.onRenderComplete(unused -> {
                    FrameLayoutWidget widget = (FrameLayoutWidget) dataBinding.recyclerView.getLayoutManager().findViewByPosition(0);
                    slideEventViewManager.setCheckViewListener(widget);
                });
            }

            @Override
            public void onError(int code, String message) {
                dismissLoading();
                ToastUtil.showToast(activity, message);
            }
        });
    }

    private void joinMeeting(String meetingNo) {
        showLoading();
        MeetingManager.joinMeeting(activity, meetingNo, new IMeetingCallback<MeetingDetailBean>() {
            @Override
            public void onSuccess(MeetingDetailBean data) {
                dismissLoading();
            }

            @Override
            public void onError(int code, String message) {
                dismissLoading();
                ToastUtil.showToast(activity, message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE) {
            ToastUtil.showToast(activity, "正在发起协作");
            ArrayList<String> inviteeUserIds = data.getStringArrayListExtra("inviteeUserIds");
            String meetingName = MeetingManager.getUserBean().getNickname() + "的快速协作";
            MeetingManager.createJoinMeeting(activity, meetingName, inviteeUserIds, new IMeetingCallback<MeetingDetailBean>() {
                @Override
                public void onSuccess(MeetingDetailBean data) {
                }

                @Override
                public void onError(int code, String message) {
                    ToastUtil.showToast(activity, message);
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
