package com.teamhelper.glass.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.teamhelper.meeting.R;

/**
 * 给xml内view设置动态属性
 *
 * @author yanchenglong
 * @time 2021/6/26
 */
public class BindingAdapterUtil {

    /**
     * 加载个人头像
     *
     * @param imageView
     */
    @BindingAdapter({"loadHead"})
    public static void loadHead(ImageView imageView, String avatarUrl) {
        Glide.with(imageView.getContext()).load(avatarUrl).placeholder(R.mipmap.ic_meeting_avatar).into(imageView);
    }

}
