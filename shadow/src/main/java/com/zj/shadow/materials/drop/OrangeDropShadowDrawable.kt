package com.zj.shadow.materials.drop

import android.graphics.Color
import com.zj.shadow.base.DropShadowDrawable
import com.zj.shadow.base.DropShadowGradientDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
class OrangeDropShadowDrawable :
    DropShadowGradientDrawable(
        Color.parseColor("#FFC77C"),
        Color.parseColor("#FB8A76"), 1000
    )

class OrangeDropShadowPressDrawable :
    DropShadowDrawable(
        Color.parseColor("#FFC77C"), 1000
    )