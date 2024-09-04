package com.magicmex.canfiree.utils.animation

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.magicmex.canfiree.R

object AnimationManager {
    fun setAnimationClickButton(context: Context): Animation =
        AnimationUtils.loadAnimation(context, R.anim.scale_animation)
}