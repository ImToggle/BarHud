package me.imtoggle.barhud.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.utils.dsl.VG
import cc.polyfrost.oneconfig.utils.dsl.mc
import me.imtoggle.barhud.hud.Bar
import me.imtoggle.barhud.hud.Hud
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import kotlin.math.max


object Health: Config(Mod("Health", ModType.HUD), "barhud/health.json") {

    @HUD(name = "Hud")
    var hud = HealthHud()

    init {
        initialize()
    }

    class HealthHud: Hud() {
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
            val wither = mc.thePlayer.getActivePotionEffect(Potion.wither)
            val poison = mc.thePlayer.getActivePotionEffect(Potion.poison)
            val heartcolor = if (poison != null) {
                poisonColor
            } else if (wither != null) {
                witherColor
            } else {
                normalColor
            }
            return heartcolor
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