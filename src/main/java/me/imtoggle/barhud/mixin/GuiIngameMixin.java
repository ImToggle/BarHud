package me.imtoggle.barhud.mixin;

import me.imtoggle.barhud.config.HotBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin {

    @Shadow protected abstract void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer player);

    @Redirect(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V"))
    private void cancel(GuiIngame instance, int x, int y, int textureX, int textureY, int width, int height) {
        if (!HotBar.INSTANCE.getHud().isEnabled()) instance.drawTexturedModalRect(x, y, textureX, textureY, width, height);
    }

    @ModifyArgs(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderHotbarItem(IIIFLnet/minecraft/entity/player/EntityPlayer;)V"))
    private void set(Args args) {
        int j = args.get(0);
        if (HotBar.INSTANCE.getHud().isEnabled()) {
            float scale = HotBar.INSTANCE.getHud().getScale();
            args.set(1, (int) (HotBar.INSTANCE.getHud().position.getX() + (j * 20 + 3) * scale));
            args.set(2, (int) (HotBar.INSTANCE.getHud().position.getY() + 3 * scale));
        }
    }

    @Redirect(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderHotbarItem(IIIFLnet/minecraft/entity/player/EntityPlayer;)V"))
    private void scale(GuiIngame instance, int index, int xPos, int yPos, float partialTicks, EntityPlayer player) {
        if (HotBar.INSTANCE.getHud().isEnabled()) {
            ItemStack item = player.inventory.mainInventory[index];
            if (item == null) return;
            RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
            float scale = HotBar.INSTANCE.getHud().getScale();
            GlStateManager.pushMatrix();
            GlStateManager.translate(xPos, yPos, 0);
            GlStateManager.scale(scale, scale, 1f);
            itemRenderer.renderItemAndEffectIntoGUI(item, 0, 0);
            itemRenderer.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, item, 0, 0, null);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
        } else {
            renderHotbarItem(index, xPos, yPos, partialTicks, player);
        }
    }
}
