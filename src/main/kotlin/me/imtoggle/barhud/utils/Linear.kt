package me.imtoggle.barhud.utils

import cc.polyfrost.oneconfig.gui.animations.Animation
import net.minecraft.client.Minecraft

class Linear(duration: Float, start: Float, end: Float, reverse: Boolean) : Animation(duration, start, end, reverse) {
    private var startTime = 0L

    init {
        startTime = Minecraft.getSystemTime()
    }

    override fun get(): Float {
        timePassed = (Minecraft.getSystemTime() - startTime).toFloat()
        if (timePassed >= duration) return start + change
        return animate(timePassed / duration) * change + start
    }

    override fun isFinished(): Boolean {
        timePassed = (Minecraft.getSystemTime() - startTime).toFloat()
        return super.isFinished()
    }

    override fun animate(x: Float): Float {
        return x
    }

}