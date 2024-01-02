package me.imtoggle.barhud

import me.imtoggle.barhud.config.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = BarHud.MODID, name = BarHud.NAME, version = BarHud.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object BarHud {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        Air
        Armor
        Exp
        Health
        HotBar
        Hunger
    }
}