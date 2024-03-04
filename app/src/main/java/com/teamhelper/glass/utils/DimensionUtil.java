package com.teamhelper.glass.utils;

import android.content.Context;

/**
 * @author yanchenglong
 * @time 2021/10/14
 */
public class DimensionUtil {

    /**
     * 将dimen值转为px，返回值类型为float
     *
     * @param context
     * @param dimensId
     * @return
     */
    public static float getDimension(Context context, int dimensId) {
        return context.getResources().getDimension(dimensId);
    }

    /**
     * 与getDimension()一样，但只保留整数部分。返回值类型为int。
     *
     * @param context
     * @param dimensId
     * @return
     */
    public static int getDimensionPixelOffset(Context context, int dimensId) {
        return context.getResources().getDimensionPixelOffset(dimensId);
    }

    /**
     * 与getDimension()一样，但只保留四舍五入后的整数部分，并且一个非零基值至少表示一个px。返回值类型为int。
     *
     * @param context
     * @param dimensId
     * @return
     */
    public static int getDimensionPixelSize(Context context, int dimensId) {
        return context.getResources().getDimensionPixelSize(dimensId);
    }
}
