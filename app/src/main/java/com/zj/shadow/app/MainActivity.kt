package com.zj.shadow.app

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zj.shadow.app.databinding.LayoutActivityBinding
import com.zj.shadow.base.DropShadowGradientDrawable
import com.zj.shadow.dp2px
import com.zj.shadow.factory.BlueStatefulDrawableFactory
import com.zj.shadow.factory.OrangeStatefulDrawableFactory
import com.zj.shadow.factory.RedStatefulDrawableFactory
import com.zj.shadow.factory.StatefulDrawableDecorate
import com.zj.shadow.materials.diffusion.ItemCardDiffusionShadowDrawable
import com.zj.shadow.materials.drop.GrayDropShadowDrawable

/**
 * Created by zhangjian on 2020/12/16 in project Shadow.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: LayoutActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.layout_activity)
        StatefulDrawableDecorate.createStatefulDrawable(BlueStatefulDrawableFactory::class.java)
            .init(binding.blueDrop)
        StatefulDrawableDecorate.createStatefulDrawable(OrangeStatefulDrawableFactory::class.java)
            .init(binding.orangeDrop)
        StatefulDrawableDecorate.createStatefulDrawable(RedStatefulDrawableFactory::class.java)
            .init(binding.redDrop)

        StatefulDrawableDecorate.createStatefulDrawable(BlueStatefulDrawableFactory::class.java)
            .init(binding.reset)

        binding.custom.background = DropShadowGradientDrawable(
            Color.parseColor("#FF646B"),
            Color.parseColor("#FE3439"),
            dp2px(this,5)
        )

        binding.card.background = ItemCardDiffusionShadowDrawable(this)

        binding.blueDrop.setOnClickListener { binding.blueDrop.isEnabled = false }
        binding.orangeDrop.setOnClickListener { binding.orangeDrop.isEnabled = false }
        binding.redDrop.setOnClickListener { binding.redDrop.isEnabled = false }


        binding.reset.setOnClickListener {
            binding.blueDrop.isEnabled = true
            binding.orangeDrop.isEnabled = true
            binding.redDrop.isEnabled = true
        }
    }
}