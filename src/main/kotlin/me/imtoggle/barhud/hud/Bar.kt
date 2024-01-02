package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.gui.animations.Animation
import cc.polyfrost.oneconfig.gui.animations.DummyAnimation
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.utils.Linear
import kotlin.math.min

open class Bar {
    private var lastPct = 0f
    private var barAnimation: Animation = DummyAnimation(0f)

    var pct = 0f
    fun drawBar(vg: VG, config: HudConfig, hud: Hud, value: Float, max: Float, color: OneColor) {
        val cPct = if (config.example) 0.5f else min(value / max, 1f)
        if (cPct != lastPct) {
            barAnimation = Linear(hud.duration, lastPct, cPct, false)
            lastPct = cPct
        }
        this.pct = barAnimation.get()
        if (pct == 0f) return
        val width = hud.width
        val height = hud.height
        val scissorHelper = ScissorHelper.INSTANCE
        val rounded = config.rounded
        val cornerRadius = config.cornerRadius
        val border = config.border
        val borderSize = config.borderSize
        val borderColor = config.borderColor
        if (rounded) {
            val scissor = scissorHelper.scissor(vg.instance, 0f, 0f, width * pct, height)
            vg.drawRoundedRect(0, 0, width, height, cornerRadius, color.rgb)
            scissorHelper.resetScissor(vg.instance, scissor)
            if (border) vg.drawHollowRoundedRect(0 - borderSize, 0 - borderSize, width + borderSize, height + borderSize, cornerRadius, borderColor.rgb, borderSize)
        } else {
            vg.drawRect(0, 0, width * pct, height, color.rgb)
            if (border) vg.drawHollowRoundedRect(0 - borderSize, 0 - borderSize, width + borderSize, height + borderSize, 0f, borderColor.rgb, borderSize)
        }
    }
}