package me.imtoggle.barhud.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.SubConfig
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.utils.NetworkUtils
import me.imtoggle.barhud.BarHud
import me.imtoggle.barhud.hud.*

object ModConfig : Config(Mod(BarHud.NAME, ModType.HUD, "/barhud.svg"), "barhud/main.json") {

    @SubConfig
    var air = Air

    @SubConfig
    var armor = Armor

    @SubConfig
    var exp = Exp

    @SubConfig
    var health = Health

    @SubConfig
    var hotbar = HotBar

    @SubConfig
    var hunger = Hunger

    override fun save() {
    }

}