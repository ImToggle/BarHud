package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.hud.BasicHud
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.utils.dsl.*


open class Hud : BasicHud() {

    @Slider(
        name = "Width",
        min = 0f, max = 200f,
    )
    var width = 80f

    @Slider(
        name = "Height",
        min = 0f, max = 200f,
    )
    var height = 8f

    @Slider(
        name = "Animation Duration",
        min = 0f, max = 500f,
    )
    var duration = 200f

    @Transient
    var config = HudConfig

    override fun draw(matrices: UMatrixStack?, x: Float, y: Float, scale: Float, example: Boolean) {
        nanoVG(true) {
            translate(x, y)
            scale(scale, scale)
            drawBars(this, example)
        }
    }

    open fun drawBars(vg: VG, example: Boolean) {
        config.updateConfig(rounded, cornerRadius, border, borderSize, borderColor, example)
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return width * scale
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return height * scale
    }

}