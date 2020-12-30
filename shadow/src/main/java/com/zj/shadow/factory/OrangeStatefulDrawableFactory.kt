package com.zj.shadow.factory

import android.graphics.drawable.StateListDrawable
import com.zj.shadow.base.LayerStateListDrawable
import com.zj.shadow.materials.drop.GrayDropShadowDrawable
import com.zj.shadow.materials.drop.OrangeDropShadowDrawable
import com.zj.shadow.materials.drop.OrangeDropShadowPressDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
class OrangeStatefulDrawableFactory : IStatefulDrawableFactory {
    override fun providerStatefulDrawableFactory(): StateListDrawable {
        val stateListDrawable = LayerStateListDrawable()
        stateListDrawable.addState(btnNormalState,
            OrangeDropShadowDrawable()
        )
        stateListDrawable.addState(btnPressState,
            OrangeDropShadowPressDrawable()
        )
        stateListDrawable.addState(btnDisableState,
            GrayDropShadowDrawable()
        )
        return stateListDrawable
    }
}