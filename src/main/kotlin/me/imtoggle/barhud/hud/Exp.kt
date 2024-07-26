package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.hud.TextHud
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.utils.dsl.VG
import cc.polyfrost.oneconfig.utils.dsl.mc
import me.imtoggle.barhud.config.Bar
import me.imtoggle.barhud.config.Hud
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer


object Exp: SubConfig("Experience", "barhud/experience.json") {

    @HUD(name = "Hud")
    var hud = ExpHud()

    init {
        initialize()
    }

    class ExpHud: Hud(1920 / 2f, 1080 - 29f + 5f, 182f, 5f, 2.5f) {
        @Color(
            name = "Experience Color"
        )
        var normalColor = OneColor(27, 200, 227)

        @Color(
            name = "Level Text Color"
        )
        var textColor = OneColor(94, 236, 12)

        @Color(
            name = "Horse JumpBar Color"
        )
        var jumpBarColor = OneColor(13, 119, 220)

        @Dropdown(
            name = "Level Text Type",
            options = ["None", "Shadow", "Full"]
        )
        var type = 2

        @Transient
        val exp = Bar()
        @Transient
        val jump = Bar()

        override fun draw(matrices: UMatrixStack?, x: Float, y: Float, scale: Float, example: Boolean) {
            super.draw(matrices, x, y, scale, example)
            if (!mc.thePlayer.isRidingHorse && mc.thePlayer.experienceLevel > 0) {
                val text = mc.thePlayer.experienceLevel.toString()
                val textWidth = mc.fontRendererObj.getStringWidth(text) * scale / 2
                val textHeight = (mc.fontRendererObj.FONT_HEIGHT + 1) * scale
                val textX = this.position.x + width / 2 * scale - textWidth
                TextRenderer.drawScaledString(text, textX, this.position.y - textHeight, textColor.rgb, TextRenderer.TextType.toType(type), scale)
            }
        }

        override fun drawBars(vg: VG, example: Boolean) {
            super.drawBars(vg, example)
            if (mc.thePlayer.isRidingHorse) {
                jump.drawBar(vg, config, this, mc.thePlayer.horseJumpPower, 1f, jumpBarColor)
            }else {
                exp.drawBar(vg, config, this, mc.thePlayer.experience, 1f, normalColor)
            }
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && (mc.playerController.shouldDrawHUD() && mc.renderViewEntity is EntityPlayer || mc.thePlayer.isRidingHorse)
        }
    }
}