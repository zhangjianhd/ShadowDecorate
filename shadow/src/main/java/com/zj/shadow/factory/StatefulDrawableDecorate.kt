package com.zj.shadow.factory

import android.graphics.drawable.Drawable
import android.view.View

/**
 * Created by zhangjian on 2020/12/21 in project shadow.
 * 组装的状态Drawable设置工具类
 */
class StatefulDrawableDecorate {

    var drawable: Drawable? = null

    private constructor()

    companion object {
        fun <T : IStatefulDrawableFactory> createStatefulDrawable(tClass: Class<T>): StatefulDrawableDecorate {
            val drawableDecorate = StatefulDrawableDecorate()
            getStatefulDrawableFactory(tClass)?.let {
                drawableDecorate.drawable = it.providerStatefulDrawableFactory()
            }
            return drawableDecorate
        }

        private fun getStatefulDrawableFactory(c: Class<*>): IStatefulDrawableFactory? {
            try {
                return c.newInstance() as IStatefulDrawableFactory
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
            return null
        }
    }

    fun init(view: View) {
        drawable?.let {
            view.background = drawable
        }
    }
}