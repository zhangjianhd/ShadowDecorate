package com.zj.shadow.materials.diffusion

import android.content.Context
import android.graphics.Color
import com.zj.shadow.base.DiffusionShadowDrawable
import com.zj.shadow.dp2px

/**
 * Created by zhangjian on 2020/12/22 in project shadow.
 * 卡片的四周阴影
 */
class ItemCardDiffusionShadowDrawable(context: Context) : DiffusionShadowDrawable(
    Color.parseColor("#ffffff"),
    Color.parseColor("#dddddd"),
    dp2px(context,6),
    dp2px(context,5)
)