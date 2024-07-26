package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.config.*
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemFood

object Hunger: SubConfig("Hunger", "barhud/hunger.json") {

    @HUD(name = "Hud")
    var hud = HungerHud()

    init {
        initialize()
    }

    class HungerHud: Hud(1920 / 2f + 182 / 2f - 81 / 2f, 1080 - 39f + 9f) {
        @Color(
            name = "Normal Color"
        )
        var normalColor = OneColor(167, 103, 0)

        @Color(
            name = "Food Color"
        )
        var foodColor = OneColor(233, 179, 93)

        @Color(
            name = "Horse Health Color"
        )
        var horseColor = OneColor(226, 37, 37)

        @Transient
        val hunger = Bar()

        @Transient
        val food = Bar()

        @Transient
        val horse = Bar()

        override fun drawBars(vg: VG, example: Boolean) {
            super.drawBars(vg, example)
            if (mc.thePlayer.isRiding && mc.thePlayer.ridingEntity is EntityLivingBase) {
                val mount = mc.thePlayer.ridingEntity as EntityLivingBase
                horse.drawBar(vg, config, this, mount.health, mount.maxHealth, horseColor)
            } else {
                val heldItem = mc.thePlayer.heldItem
                if (heldItem != null && heldItem.item is ItemFood) {
                    val foodValue = 20f * hunger.pct + (heldItem.item as ItemFood).getHealAmount(heldItem)
                    food.drawBar(vg, config, this, foodValue, 20f, foodColor)
                }
                hunger.drawBar(vg, config, this, mc.thePlayer.foodStats.foodLevel.toFloat(), 20f, normalColor)
            }
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && mc.playerController.shouldDrawHUD() && mc.renderViewEntity is EntityPlayer
        }
    }
}