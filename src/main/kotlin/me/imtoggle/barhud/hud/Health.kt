package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.utils.dsl.VG
import cc.polyfrost.oneconfig.utils.dsl.mc
import me.imtoggle.barhud.config.Bar
import me.imtoggle.barhud.config.Hud
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import kotlin.math.max


object Health: SubConfig("Health", "barhud/health.json") {

    @HUD(name = "Hud")
    var hud = HealthHud()

    init {
        initialize()
    }

    class HealthHud: Hud(1920 / 2f - 182 / 2f + 81 / 2f, 1080 - 39f + 9f) {
        @Color(
            name = "Normal Color"
        )
        var normalColor = OneColor(226, 37, 37)

        @Color(
            name = "Poison Color"
        )
        var poisonColor = OneColor(29, 193, 58)

        @Color(
            name = "Wither Color"
        )
        var witherColor = OneColor(27, 27, 27)

        @Color(name = "Absorption Color")
        var absorptionColor = OneColor(243, 169, 16)

        @Transient
        val health = Bar()

        @Transient
        val absorption = Bar()

        private fun getColor(): OneColor {
            mc.thePlayer.getActivePotionEffect(Potion.poison)?.let { return poisonColor }
            mc.thePlayer.getActivePotionEffect(Potion.wither)?.let { return witherColor }
            return normalColor
        }

        override fun drawBars(vg: VG, example: Boolean) {
            super.drawBars(vg, example)
            val max = max(mc.thePlayer.maxHealth, mc.thePlayer.absorptionAmount)
            health.drawBar(vg, config, this, mc.thePlayer.health, mc.thePlayer.maxHealth, getColor())
            if (mc.thePlayer.absorptionAmount > 0) absorption.drawBar(vg, config, this, mc.thePlayer.absorptionAmount, max, absorptionColor)
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && mc.playerController.shouldDrawHUD() && mc.renderViewEntity is EntityPlayer
        }
    }
}