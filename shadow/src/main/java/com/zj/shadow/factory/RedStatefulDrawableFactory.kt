package com.zj.shadow.factory

import android.graphics.drawable.StateListDrawable
import com.zj.shadow.base.LayerStateListDrawable
import com.zj.shadow.materials.drop.GrayDropShadowDrawable
import com.zj.shadow.materials.drop.RedDropShadowDrawable
import com.zj.shadow.materials.drop.RedDropShadowPressDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
class RedStatefulDrawableFactory : IStatefulDrawableFactory {
    override fun providerStatefulDrawableFactory(): StateListDrawable {
        val stateListDrawable = LayerStateListDrawable()
        stateListDrawable.addState(btnNormalState,
            RedDropShadowDrawable()
        )
        stateListDrawable.addState(btnPressState,
            RedDropShadowPressDrawable()
        )
        stateListDrawable.addState(btnDisableState,
            GrayDropShadowDrawable()
        )
        return stateListDrawable
    }
}