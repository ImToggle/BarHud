package me.imtoggle.barhud.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.hud.*
import net.minecraft.entity.player.EntityPlayer


object Exp: Config(Mod("Experience", ModType.HUD), "barhud/experience.json") {

    @HUD(name = "Hud")
    var hud = ExpHud()

    init {
        initialize()
    }

    class ExpHud: Hud() {
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

        override fun drawBars(vg: VG, example: Boolean) {
            super.drawBars(vg, example)
            if (mc.thePlayer.isRidingHorse) {
                jump.drawBar(vg, config, this, mc.thePlayer.horseJumpPower, 1f, jumpBarColor)
            }else {
                exp.drawBar(vg, config, this, mc.thePlayer.experience, 1f, normalColor)
                if (mc.thePlayer.experienceLevel > 0) {
                    val text = mc.thePlayer.experienceLevel.toString()
                    val textWidth = mc.fontRendererObj.getStringWidth(text) * scale / 2
                    val textHeight = (mc.fontRendererObj.FONT_HEIGHT + 1) * scale
                    val textX: Float = this.position.x + width / 2 * scale - textWidth
                    TextRenderer.drawScaledString(text, textX, this.position.y - textHeight, textColor.rgb, TextRenderer.TextType.toType(type), scale)
                }
            }
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && (mc.playerController.shouldDrawHUD() && mc.renderViewEntity is EntityPlayer || mc.thePlayer.isRidingHorse)
        }
    }
}