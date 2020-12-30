package com.zj.shadow.base;

import android.graphics.drawable.StateListDrawable;

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 */
public class LayerStateListDrawable extends StateListDrawable {

    @Override
    public boolean isProjected() {
        return true;
    }
}
