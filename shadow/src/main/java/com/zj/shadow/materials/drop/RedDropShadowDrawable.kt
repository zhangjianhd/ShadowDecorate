package com.zj.shadow.materials.drop

import android.graphics.Color
import com.zj.shadow.base.DropShadowDrawable
import com.zj.shadow.base.DropShadowGradientDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
class RedDropShadowDrawable :
    DropShadowGradientDrawable(
        Color.parseColor("#FF646B"),
        Color.parseColor("#FE3439"), 1000
    )

class RedDropShadowPressDrawable :
    DropShadowDrawable(
        Color.parseColor("#FF646B"), 1000
    )