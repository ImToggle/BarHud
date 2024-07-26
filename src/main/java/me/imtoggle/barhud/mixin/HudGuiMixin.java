package me.imtoggle.barhud.mixin;

import cc.polyfrost.oneconfig.hud.Hud;
import me.imtoggle.barhud.hud.*;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "cc.polyfrost.oneconfig.internal.gui.HudGui")
public class HudGuiMixin {

    @Redirect(method = "onDrawScreen", at = @At(value = "INVOKE", target = "Lcc/polyfrost/oneconfig/hud/Hud;isEnabled()Z"))
    private boolean vanillahud(Hud hud) {
        boolean isVanillaHUD = hud.getClass().getPackage().getName().contains("org.polyfrost.vanillahud.hud");
        return isVanillaHUD ? shouldBeDrawn(hud) : hud.isEnabled();
    }

    @Unique
    private boolean shouldBeDrawn(Hud hud) {
        switch (hud.getClass().getSimpleName()) {
            case "AirHud": return !Air.INSTANCE.getHud().isEnabled();
            case "ArmorHud": return !Armor.INSTANCE.getHud().isEnabled();
            case "ExperienceHud": return !Exp.INSTANCE.getHud().isEnabled();
            case "HealthHud": return !Health.INSTANCE.getHud().isEnabled();
            case "HotBarHud": return !HotBar.INSTANCE.getHud().isEnabled();
            case "HungerHud":
            case "MountHud": return !Hunger.INSTANCE.getHud().isEnabled();
        }
        return hud.isEnabled();
    }
}
