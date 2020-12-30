package com.zj.shadow

import android.content.Context
import android.util.TypedValue

/**
 * Created by zhangjian on 2020/12/30 in project Shadow.
 */
fun dp2px(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}