package com.zj.shadow.factory

import android.graphics.drawable.StateListDrawable

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
interface IStatefulDrawableFactory {
    fun providerStatefulDrawableFactory(): StateListDrawable
}
