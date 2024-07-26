package me.imtoggle.barhud.config

import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.BasicHud
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.utils.dsl.VG
import cc.polyfrost.oneconfig.utils.dsl.nanoVG
import cc.polyfrost.oneconfig.utils.dsl.scale
import cc.polyfrost.oneconfig.utils.dsl.translate
import net.minecraft.client.renderer.GlStateManager


open class Hud(x: Float, y: Float, val defaultWidth: Float = 81f, val defaultHeight: Float = 9f, radius: Float = 4f) : BasicHud(true, x, y, 1f, true, true, radius, 0f, 0f, OneColor(0, 0, 0, 120), false, 2f, OneColor(0, 0, 0)) {

    @Slider(
        name = "Width",
        min = 0f, max = 200f,
    )
    var width = 81f

    @Slider(
        name = "Height",
        min = 0f, max = 200f,
    )
    var height = 9f

    @Slider(
        name = "Animation Duration",
        min = 0f, max = 500f,
    )
    var duration = 200f

    @Transient
    var config = HudConfig

    init {
        this.width = defaultWidth
        this.height = defaultHeight
    }

    override fun resetPosition() {
        width = defaultWidth
        height = defaultHeight
        super.resetPosition()
    }

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