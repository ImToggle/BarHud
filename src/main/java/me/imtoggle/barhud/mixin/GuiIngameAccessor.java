package me.imtoggle.barhud.mixin;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiIngame.class)
public interface GuiIngameAccessor {
    @Invoker("renderTooltip")
    void renderHotBar(ScaledResolution sr, float partialTicks);

    @Accessor
    ResourceLocation getWidgetsTexPath();
}
