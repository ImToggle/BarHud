package me.imtoggle.barhud.mixin;

import me.imtoggle.barhud.config.HotBar;
import net.minecraft.client.gui.GuiSpectator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GuiSpectator.class)
public class GuiSpectatorMixin {
    @Redirect(method = "func_175258_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSpectator;drawTexturedModalRect(FFIIII)V"))
    private void cancel(GuiSpectator instance, float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
        if (!HotBar.INSTANCE.getHud().isEnabled()) instance.drawTexturedModalRect(xCoord, yCoord, minU, minV, maxU, maxV);
    }

    @ModifyArgs(method = "func_175258_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSpectator;func_175266_a(IIFFLnet/minecraft/client/gui/spectator/ISpectatorMenuObject;)V"))
    private void cancel(Args args) {
        int j = args.get(0);
        if (HotBar.INSTANCE.getHud().isEnabled()) args.set(1, (int) (HotBar.INSTANCE.getHud().position.getX() / HotBar.INSTANCE.getHud().getScale() + j * 20 + 2));
    }
}
