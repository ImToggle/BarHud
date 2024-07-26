package me.imtoggle.barhud.mixin;

import me.imtoggle.barhud.hud.*;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiIngameForge.class, remap = false, priority = 800)
public class GuiIngameForgeMixin {
    @Inject(method = "renderJumpBar", at = @At("HEAD"), cancellable = true)
    private void cancelHorsePower(CallbackInfo ci) {
        if (Exp.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

    @Inject(method = "renderHealthMount", at = @At("HEAD"), cancellable = true)
    private void cancelHorseHealth(CallbackInfo ci) {
        if (Hunger.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

    @Inject(method = "renderExperience", at = @At("HEAD"), cancellable = true)
    private void cancelExpBar(CallbackInfo ci) {
        if (Exp.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

    @Inject(method = "renderHealth", at = @At("HEAD"), cancellable = true)
    private void cancelHealth(CallbackInfo ci) {
        if (Health.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void cancelHunger(CallbackInfo ci) {
        if (Hunger.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    private void cancelArmor(CallbackInfo ci) {
        if (Armor.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

    @Inject(method = "renderAir", at = @At("HEAD"), cancellable = true)
    private void cancelAir(CallbackInfo ci) {
        if (Air.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

    @Inject(method = "renderTooltip", at = @At("HEAD"), cancellable = true, remap = true)
    private void cancelHotbar(CallbackInfo ci) {
        if (HotBar.INSTANCE.getHud().isEnabled()) ci.cancel();
    }

}
