package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.config.*
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.MathHelper


object Air: SubConfig("Air", "barhud/air.json") {

    @HUD(name = "Hud")
    var hud = AirHud()

    init {
        initialize()
    }

    class AirHud: Hud(1920 / 2f + 182 / 2f - 81 / 2f, 1080 - 49f + 9f) {
        @Color(
            name = "Color"
        )
        var normalColor = OneColor(13, 119, 220)

        @Transient
        val air = Bar()

        override fun drawBars(vg: VG, example: Boolean) {
            super.drawBars(vg, example)
            val value = MathHelper.ceiling_double_int(mc.thePlayer.air.toDouble() * 20.0 / 300.0)
            air.drawBar(vg, config, this, value.toFloat(), 20f, normalColor)
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && mc.playerController.shouldDrawHUD() && mc.renderViewEntity is EntityPlayer && mc.thePlayer.isInsideOfMaterial(Material.water)
        }
    }
}