package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.core.OneColor

object HudConfig {
    var rounded = false
    var cornerRadius = 0f
    var border = false
    var borderSize = 0f
    var borderColor = OneColor(0xFFFFFF)
    var example = false

    fun updateConfig(rounded: Boolean, cornerRadius: Float, border: Boolean, borderSize: Float, borderColor: OneColor, example: Boolean) {
        this.rounded = rounded
        this.cornerRadius = cornerRadius
        this.border = border
        this.borderSize = borderSize
        this.borderColor = borderColor
        this.example = example
    }
}