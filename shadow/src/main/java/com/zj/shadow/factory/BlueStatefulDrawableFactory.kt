package com.zj.shadow.factory

import android.graphics.drawable.StateListDrawable
import com.zj.shadow.base.LayerStateListDrawable
import com.zj.shadow.materials.drop.BlueDropShadowDrawable
import com.zj.shadow.materials.drop.BlueDropShadowPressDrawable
import com.zj.shadow.materials.drop.GrayDropShadowDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
class BlueStatefulDrawableFactory : IStatefulDrawableFactory {
    override fun providerStatefulDrawableFactory(): StateListDrawable {
        val stateListDrawable = LayerStateListDrawable()
        stateListDrawable.addState(btnNormalState,
            BlueDropShadowDrawable()
        )
        stateListDrawable.addState(btnPressState,
            BlueDropShadowPressDrawable()
        )
        stateListDrawable.addState(btnDisableState,
            GrayDropShadowDrawable()
        )
        return stateListDrawable
    }
}