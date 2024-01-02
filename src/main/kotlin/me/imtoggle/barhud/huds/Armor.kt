package me.imtoggle.barhud.huds

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.hud.*
import net.minecraft.entity.player.EntityPlayer

object Armor: Config(Mod("Defence", ModType.HUD, "/barhud.svg"), "barhud/defence.json") {

    @HUD(name = "Hud")
    var hud = DefenceHud()

    init {
        initialize()
    }

    class DefenceHud: Hud() {
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