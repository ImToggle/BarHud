package me.imtoggle.barhud.utils

import cc.polyfrost.oneconfig.gui.animations.Animation

class Linear(duration: Float, start: Float, end: Float, reverse: Boolean) : Animation(duration, start, end, reverse) {
    override fun animate(x: Float): Float {
        return x
    }

}