package com.zj.shadow.materials.drop

import android.graphics.Color
import com.zj.shadow.base.DropShadowDrawable
import com.zj.shadow.base.DropShadowGradientDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
class BlueDropShadowDrawable :
    DropShadowGradientDrawable(
        Color.parseColor("#16D9D3"),
        Color.parseColor("#35CBDB"), 1000
    )

class BlueDropShadowPressDrawable :
    DropShadowDrawable(
        Color.parseColor("#16D9D3"), 1000
    )