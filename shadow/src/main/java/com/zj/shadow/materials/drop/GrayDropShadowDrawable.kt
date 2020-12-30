package com.zj.shadow.materials.drop

import android.graphics.Color
import com.zj.shadow.base.DropShadowDrawable
import com.zj.shadow.base.DropShadowGradientDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
class GrayDropShadowDrawable :
    DropShadowGradientDrawable(Color.parseColor("#cccccc"),Color.parseColor("#bbbbbb"), 1000)

class GrayPressDropShadowDrawable :
    DropShadowGradientDrawable(Color.parseColor("#cdcdcd"),Color.parseColor("#bcbcbc"), 1000)