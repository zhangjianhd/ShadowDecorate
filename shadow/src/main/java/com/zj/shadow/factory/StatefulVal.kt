package com.zj.shadow.factory

import android.R

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
const val pressed: Int = R.attr.state_pressed
const val enabled = R.attr.state_enabled
const val window_focused = R.attr.state_window_focused
const val focused = R.attr.state_focused
const val selected = R.attr.state_selected

val btnNormalState = intArrayOf(enabled, -pressed)  //普通状态
val btnPressState = intArrayOf(enabled, pressed)  //点击状态
val btnDisableState = intArrayOf(-enabled)  //disable模式