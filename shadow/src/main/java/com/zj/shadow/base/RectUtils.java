package com.zj.shadow.base;

import android.graphics.RectF;

/**
 * Created by zhangjian on 2020/12/22 in project shadow.
 */
public class RectUtils {
    public static void mirrorX(RectF rectF, float centX) {
        float oldLeft = rectF.left;
        float oldRight = rectF.right;
        rectF.left = 2 * centX - oldRight;
        rectF.right = 2 * centX - oldLeft;
    }

    public static void mirrorY(RectF rectF, float centY) {
        float oldTop = rectF.top;
        float oldBottom = rectF.bottom;
        rectF.top = 2 * centY - oldBottom;
        rectF.bottom = 2 * centY - oldTop;
    }
}
