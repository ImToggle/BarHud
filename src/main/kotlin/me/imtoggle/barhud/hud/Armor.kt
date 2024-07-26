package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.config.*
import net.minecraft.entity.player.EntityPlayer

object Armor: SubConfig("Armor", "barhud/defence.json") {

    @HUD(name = "Hud")
    var hud = DefenceHud()

    init {
        initialize()
    }

    class DefenceHud: Hud(1920 / 2f - 182 / 2f + 81 / 2f, 1080 - 49f + 9f) {
        @Color(
            name = "Color"
        )
        var normalColor = OneColor(100, 100, 100)

        @Transient
        val armor = Bar()

        override fun drawBars(vg: VG, example: Boolean) {
            super.drawBars(vg, example)
            armor.drawBar(vg, config, this, mc.thePlayer.totalArmorValue.toFloat(), 20f, normalColor)
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && mc.playerController.shouldDrawHUD() && mc.renderViewEntity is EntityPlayer && (mc.thePlayer.totalArmorValue > 0 || armor.pct != 0f)
        }
    }
}